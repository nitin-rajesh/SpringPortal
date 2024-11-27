import React, { useEffect, useState } from "react";
import axios from "axios";

const UserProfile = () => {
  const [studentInfo, setStudentInfo] = useState(null);
  const [specialisations, setSpecialisations] = useState([]);
  const [domain, setDomain] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchStudentInfo = async () => {
      const jwtToken = localStorage.getItem("jwtToken");
      const studentId = localStorage.getItem("studentId");

      if (!jwtToken || !studentId) {
        setError("Authorization token or student ID is missing.");
        return;
      }

      try {
        const response = await axios.get(`http://localhost:8080/students/info/${studentId}`, {
          headers: {
            Accept: "application/json",
            Authorization: `Bearer ${jwtToken}`,
          },
        });

        if (response.data.length > 0) {
          const firstEntry = response.data[0];
          setStudentInfo(firstEntry.student);
          setSpecialisations(response.data.map((item) => item.specialisation));
          setDomain(firstEntry.domain); // Extract domain information from the first element
        } else {
          setError("No student information found.");
        }
      } catch (err) {
        setError("Failed to fetch student information. Please try again.");
      }
    };

    fetchStudentInfo();
  }, []);

  return (
    <div style={{ padding: "20px", fontFamily: "Arial, sans-serif", textAlign: "center" }}>
      <h1 style={{ marginBottom: "20px", color: "gray"}}>User Profile</h1>
      {error && <p style={{ color: "red" }}>{error}</p>}
      {studentInfo && (
        <div style={{ marginBottom: "20px" }}>
          <h2>{studentInfo.firstName} {studentInfo.lastName}</h2>
          <p>
            <strong>Roll Number:</strong> {studentInfo.rollNumber}
          </p>
          <p>
            <strong>Email:</strong> {studentInfo.email}
          </p>
          <p>
            <strong>CGPA:</strong> {studentInfo.cgpa}/4 [{(studentInfo.cgpa * 2.5).toFixed(1)}/10]
          </p>
          <p>
            <strong>Graduation Year:</strong> {studentInfo.gradYear}
          </p>
        </div>
      )}

      {domain && (
        <div style={{ marginBottom: "20px" }}>
          <h3>Domain Information</h3>
          <p>
            <strong>Program:</strong> {domain.program}
          </p>
          <p>
            <strong>Batch:</strong> {domain.batch}
          </p>
          <p>
            <strong>Capacity:</strong> {domain.capacity}
          </p>
          <p>
            <strong>Qualification:</strong> {domain.qualification}
          </p>
        </div>
      )}

      {specialisations.length > 0 && (
        <div>
          <h3>Specialisations</h3>
          <table
            style={{
              width: "80%",
              margin: "20px auto",
              borderCollapse: "collapse",
              textAlign: "center",
            }}
          >
            <thead>
              <tr>
                <th style={{ border: "1px solid #ddd", padding: "8px" }}>Code</th>
                <th style={{ border: "1px solid #ddd", padding: "8px" }}>Name</th>
                <th style={{ border: "1px solid #ddd", padding: "8px" }}>Description</th>
                <th style={{ border: "1px solid #ddd", padding: "8px" }}>Year</th>
                <th style={{ border: "1px solid #ddd", padding: "8px" }}>Credits Required</th>
              </tr>
            </thead>
            <tbody>
              {specialisations.map((spec, index) => (
                <tr key={index}>
                  <td style={{ border: "1px solid #ddd", padding: "8px" }}>{spec.code}</td>
                  <td style={{ border: "1px solid #ddd", padding: "8px" }}>{spec.name}</td>
                  <td style={{ border: "1px solid #ddd", padding: "8px" }}>{spec.description}</td>
                  <td style={{ border: "1px solid #ddd", padding: "8px" }}>{spec.year}</td>
                  <td style={{ border: "1px solid #ddd", padding: "8px" }}>{spec.creditsRequired}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default UserProfile;
