import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [responseData, setResponseData] = useState(null); // State to store the API response
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault(); // Prevent form submission from refreshing the page
    setMessage(""); // Clear any existing messages
    setResponseData(null); // Clear previous API response

    try {
      const response = await axios.post("http://localhost:8080/login", {
        username,
        password,
      });

      if (response.status === 200) {
        const { jwtToken, studentId } = response.data;

        // Store jwtToken and studentId in localStorage
        localStorage.setItem("jwtToken", jwtToken);
        localStorage.setItem("studentId", studentId);
  
        setMessage("Login successful!");
        navigate("/home"); // Navigate to HomePage

      } else {
        setMessage("Login failed. Please try again.");
      }
    } catch (error) {
      setMessage(
        error.response?.data?.message || "An error occurred. Please try again."
      );
    }
  };

  return (
    <div style={{ maxWidth: "400px", margin: "auto", textAlign: "center" }}>
      <h2>Student Placement ERP</h2>
      <br></br>
      <form onSubmit={handleLogin}>
        <div style={{ marginBottom: "1rem" }}>
          <input
            type="text"
            placeholder="Username/Email"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
            style={{ padding: "0.5rem", width: "100%" }}
          />
        </div>
        <div style={{ marginBottom: "1rem" }}>
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            style={{ padding: "0.5rem", width: "100%" }}
          />
        </div>
        <button type="submit" style={{ padding: "0.5rem 2rem" }}>
          Login
        </button>
      </form>

      {message && <p>{message}</p>}
      
      {responseData && (
        <div style={{ marginTop: "1rem", textAlign: "left" }}>
          <h3>Response:</h3>
          <p><strong>JWT Token:</strong> {responseData.jwtToken}</p>
          <p><strong>Student ID:</strong> {responseData.studentId}</p>
        </div>
      )}
    </div>
  );
};

export default Login;
