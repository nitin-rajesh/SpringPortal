import React, { useEffect, useState } from "react";
import axios from "axios";

const HomePage = () => {
  const [placements, setPlacements] = useState([]);
  const [openApplications, setOpenApplications] = useState([]);
  const [error, setError] = useState("");
  const [message, setMessage] = useState("");

  // Fetch placements on component mount
  useEffect(() => {
    const fetchPlacements = async () => {
      const jwtToken = localStorage.getItem("jwtToken"); // Retrieve JWT token
      const studentId = localStorage.getItem("studentId"); // Retrieve student ID

      if (!jwtToken || !studentId) {
        setError("Authorization token or student ID is missing.");
        return;
      }

      try {
        // Fetch placements available for student
        const placementsResponse = await axios.get(
          `http://localhost:8080/placements/student/${studentId}`,
          {
            headers: {
              Accept: "application/json",
              Authorization: `Bearer ${jwtToken}`,
            },
          }
        );

        setPlacements(placementsResponse.data); // Set fetched placements
      } catch (err) {
        setError("Failed to fetch placements. Please try again.");
      }

      try {
        // Fetch open applications (placements applied by student)
        const openAppsResponse = await axios.get(
          `http://localhost:8080/placement-student/${studentId}`,
          {
            headers: {
              Accept: "application/json",
              Authorization: `Bearer ${jwtToken}`,
              Cookie: "JSESSIONID=E0A4CD6C26783C50A95772BF8207AC7F", // Use relevant JSESSIONID if needed
            },
          }
        );

        setOpenApplications(openAppsResponse.data); // Set open applications data
      } catch (err) {
        setError("Failed to fetch open applications. Please try again.");
      }
    };

    fetchPlacements();
  }, []);

  // Handle apply button click for placements
  const handleApply = async (placementId) => {
    const jwtToken = localStorage.getItem("jwtToken"); // Retrieve JWT token
    const studentId = localStorage.getItem("studentId"); // Retrieve student ID

    if (!jwtToken || !studentId) {
      setMessage("You are not authorized to apply.");
      return;
    }

    try {
      const response = await axios.post(
        "http://localhost:8080/placement-student/apply",
        {
          placementId,
          studentId,
        },
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${jwtToken}`,
            Cookie: "JSESSIONID=E0A4CD6C26783C50A95772BF8207AC7F",
          },
        }
      );

      setMessage("You have successfully applied for the placement!");
    } catch (err) {
      setMessage("Failed to apply for placement. Please try again.");
      console.error(err);
    }
  };

  return (
    <div style={{ padding: "20px", fontFamily: "Arial, sans-serif" }}>
      <h1 style={{ textAlign: "center", marginBottom: "20px" }}>
        Student Placement Portal
      </h1>

      {error && <p style={{ color: "red", textAlign: "center" }}>{error}</p>}
      {message && <p style={{ color: "green", textAlign: "center" }}>{message}</p>}

      {/* Placements Table */}
      <h2>Available Placements</h2>
      <table style={{ width: "100%", borderCollapse: "collapse" }}>
        <thead>
          <tr>
            <th style={{ border: "1px solid #ddd", padding: "8px" }}>
              Organisation Name
            </th>
            <th style={{ border: "1px solid #ddd", padding: "8px" }}>Profile</th>
            <th style={{ border: "1px solid #ddd", padding: "8px" }}>
              Address
            </th>
            <th style={{ border: "1px solid #ddd", padding: "8px" }}>
              Min Grade
            </th>
            <th style={{ border: "1px solid #ddd", padding: "8px" }}>Action</th>
          </tr>
        </thead>
        <tbody>
          {placements.length > 0 ? (
            placements.map((placement) => (
              <tr key={placement.id}>
                <td style={{ border: "1px solid #ddd", padding: "8px" }}>
                  {placement.organisation.name}
                </td>
                <td style={{ border: "1px solid #ddd", padding: "8px" }}>
                  {placement.profile}
                </td>
                <td style={{ border: "1px solid #ddd", padding: "8px" }}>
                  {placement.organisation.address}
                </td>
                <td style={{ border: "1px solid #ddd", padding: "8px" }}>
                  {placement.minGrade}
                </td>
                <td style={{ border: "1px solid #ddd", padding: "8px" }}>
                  <button
                    onClick={() => handleApply(placement.id)}
                    style={{
                      padding: "6px 12px",
                      backgroundColor: "#007bff",
                      color: "#fff",
                      border: "none",
                      borderRadius: "4px",
                      cursor: "pointer",
                    }}
                  >
                    Apply
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="5" style={{ textAlign: "center", padding: "8px" }}>
                No placements available.
              </td>
            </tr>
          )}
        </tbody>
      </table>
      <hr class="solid"></hr>
      {/* Open Applications Table */}
      <h2>Open Applications</h2>
      <table style={{ width: "100%", borderCollapse: "collapse", marginTop: "20px" }}>
        <thead>
          <tr>
            <th style={{ border: "1px solid #ddd", padding: "8px" }}>
              Organisation Name
            </th>
            <th style={{ border: "1px solid #ddd", padding: "8px" }}>Profile</th>
            <th style={{ border: "1px solid #ddd", padding: "8px" }}>
              Address
            </th>
            <th style={{ border: "1px solid #ddd", padding: "8px" }}>
              Min Grade
            </th>
          </tr>
        </thead>
        <tbody>
          {openApplications.length > 0 ? (
            openApplications.map((application) => (
              <tr key={application.id}>
                <td style={{ border: "1px solid #ddd", padding: "8px" }}>
                  {application.placement.organisation.name}
                </td>
                <td style={{ border: "1px solid #ddd", padding: "8px" }}>
                  {application.placement.profile}
                </td>
                <td style={{ border: "1px solid #ddd", padding: "8px" }}>
                  {application.placement.organisation.address}
                </td>
                <td style={{ border: "1px solid #ddd", padding: "8px" }}>
                  {application.placement.minGrade}
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="4" style={{ textAlign: "center", padding: "8px" }}>
                No open applications.
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default HomePage;
