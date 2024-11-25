import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Register = () => {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    rollNumber: "",
    email: "",
    photoPath: "",
    cgpa: "",
    totalCreds: "",
    gradYear: "",
    password: "",
  });
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    setMessage("");

    try {
      const response = await axios.post(
        "http://localhost:8080/register",
        {
          ...formData,
          accessToken: null,
        },
        { withCredentials: true } // To include cookies in the request
      );

      if (response.status === 201) {
        setMessage("User registered successfully!");
        navigate("/"); // Redirect to login page
      } else {
        setMessage("Registration failed. Please try again.");
      }
    } catch (error) {
      setMessage(
        error.response?.data?.message || "An error occurred. Please try again."
      );
    }
  };

  return (
    <div style={{ maxWidth: "400px", margin: "auto", textAlign: "center" }}>
      <h2>Register</h2>
      <form onSubmit={handleRegister}>
        {Object.keys(formData).map((field) => (
          <div key={field} style={{ marginBottom: "1rem" }}>
            <input
              type={field === "password" ? "password" : "text"}
              placeholder={field}
              name={field}
              value={formData[field]}
              onChange={handleChange}
              required
              style={{ padding: "0.5rem", width: "100%" }}
            />
          </div>
        ))}
        <button type="submit" style={{ padding: "0.5rem 2rem" }}>
          Create User
        </button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
};

export default Register;
