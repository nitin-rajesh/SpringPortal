import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate(); // For navigation

  const handleLogin = async (e) => {
    e.preventDefault();
    setMessage("");

    try {
      const response = await axios.post("http://localhost:8080/login", {
        username,
        password,
      });

      if (response.status === 200) {
        setMessage("Login successful!");
      } else {
        setMessage("Login failed. Please try again.");
      }
    } catch (error) {
      setMessage(
        error.response?.data?.message || "An error occurred. Please try again."
      );
    }
  };

  const goToRegister = () => {
    navigate("/register"); // Navigate to the register page
  };

  return (
    <div style={{ maxWidth: "400px", margin: "auto", textAlign: "center" }}>
      <h2>Login</h2>
      <form onSubmit={handleLogin}>
        <div style={{ marginBottom: "1rem" }}>
          <input
            type="text"
            placeholder="Username"
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

      <button
        onClick={goToRegister}
        style={{
          padding: "0.5rem 2rem",
          marginTop: "1rem",
          backgroundColor: "#007BFF",
          color: "#fff",
          border: "none",
          cursor: "pointer",
        }}
      >
        Register
      </button>

      {message && <p>{message}</p>}
    </div>
  );
};

export default Login;
