import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const HomePage = () => {
  const [placements, setPlacements] = useState([]);
  const [openApplications, setOpenApplications] = useState([]);
  const [error, setError] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  // Fetch placements on component mount
  useEffect(() => {
    const fetchPlacements = async () => {
      const jwtToken = localStorage.getItem("jwtToken");
      const studentId = localStorage.getItem("studentId");

      if (!jwtToken || !studentId) {
        setError("Authorization token or student ID is missing.");
        return;
      }

      try {
        const placementsResponse = await axios.get(
          `http://localhost:8080/placements/student/${studentId}`,
          {
            headers: {
              Accept: "application/json",
              Authorization: `Bearer ${jwtToken}`,
            },
          }
        );
        setPlacements(placementsResponse.data);
      } catch (err) {
        setError("Failed to fetch placements. Please try again.");
      }

      try {
        const openAppsResponse = await axios.get(
          `http://localhost:8080/placement-student/${studentId}`,
          {
            headers: {
              Accept: "application/json",
              Authorization: `Bearer ${jwtToken}`,
              Cookie: "JSESSIONID=E0A4CD6C26783C50A95772BF8207AC7F",
            },
          }
        );
        setOpenApplications(openAppsResponse.data);
      } catch (err) {
        setError("Failed to fetch open applications. Please try again.");
      }
    };

    fetchPlacements();
  }, []);

  const handleApply = async (placementId) => {
    const jwtToken = localStorage.getItem("jwtToken");
    const studentId = localStorage.getItem("studentId");

    if (!jwtToken || !studentId) {
      setMessage("You are not authorized to apply.");
      return;
    }

    try {
      await axios.post(
        "http://localhost:8080/placement-student/apply",
        { placementId, studentId },
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

  const handleProfileClick = () => {
    navigate("/profile");
  };

  const handleResumeUpload = async (applicationId, file) => {
    const jwtToken = localStorage.getItem("jwtToken");

    if (!jwtToken) {
      setMessage("You are not authorized to upload a resume.");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {
      await axios.post(
        `http://localhost:8080/placement-student/${applicationId}/upload-cv`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: `Bearer ${jwtToken}`,
            Cookie: "JSESSIONID=E0A4CD6C26783C50A95772BF8207AC7F",
          },
        }
      );
      setMessage("Resume uploaded successfully!");
    } catch (err) {
      setMessage("Failed to upload resume. Please try again.");
      console.error(err);
    }
  };

  const downloadResume = (id) => {
    const jwtToken = localStorage.getItem("jwtToken");
    const studentId = localStorage.getItem("studentId");

    fetch(`http://localhost:8080/placement-student/${id}/download-cv`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
    })
      .then((response) => {
        if (response.ok) {
          return response.blob();
        }
        throw new Error("Failed to download the resume");
      })
      .then((blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement("a");
        a.href = url;
        a.download = `resume_${id}.pdf`; // Default filename
        a.click();
        window.URL.revokeObjectURL(url);
      })
      .catch((error) => console.error("Error downloading resume:", error));
  };


  const handleFileChange = (event, applicationId) => {
    const file = event.target.files[0];
    if (file) {
      handleResumeUpload(applicationId, file);
    }
  };

  return (
    <div style={{ padding: "20px", fontFamily: "Arial, sans-serif" }}>
      <h1 style={{ textAlign: "center", marginBottom: "20px" }}>
        Student Placement Portal
      </h1>

      {error && <p style={{ color: "red", textAlign: "center" }}>{error}</p>}
      {message && <p style={{ color: "green", textAlign: "center" }}>{message}</p>}

      <h2>Available Placements</h2>
      <table style={{ width: "100%", borderCollapse: "collapse", border: "1px solid black" }}>
        <thead>
          <tr>
            <th style={{ border: "1px solid #ddd", padding: "8px" }}>Organisation Name</th>
            <th style={{ border: "1px solid #ddd", padding: "8px" }}>Profile</th>
            <th style={{ border: "1px solid #ddd", padding: "8px" }}>Address</th>
            <th style={{ border: "1px solid #ddd", padding: "8px" }}>Min Grade</th>
            <th style={{ border: "1px solid #ddd", padding: "8px" }}>Action</th>
          </tr>
        </thead>
        <tbody>
          {placements.length > 0 ? (
            placements.map((placement) => (
              <tr key={placement.id}>
                <td style={{ border: "1px solid #ddd", padding: "8px" }}>{placement.organisation.name}</td>
                <td style={{ border: "1px solid #ddd", padding: "8px" }}>{placement.profile}</td>
                <td style={{ border: "1px solid #ddd", padding: "8px" }}>{placement.organisation.address}</td>
                <td style={{ border: "1px solid #ddd", padding: "8px" }}>{placement.minGrade}</td>
                <td style={{ border: "1px solid #ddd", padding: "8px" }}>
                  <button onClick={() => handleApply(placement.id)}>Apply</button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="5">No placements available.</td>
            </tr>
          )}
        </tbody>
      </table>
      <hr></hr>
      <h2>Open Applications</h2>
      <table style={{ width: "100%", borderCollapse: "collapse", marginTop: "20px" }}>
        <thead>
          <tr>
            <th style={{ border: "1px solid #ddd", padding: "8px" }}>Organisation Name</th>
            <th style={{ border: "1px solid #ddd", padding: "8px" }}>Profile</th>
            <th style={{ border: "1px solid #ddd", padding: "8px" }}>Address</th>
            <th style={{ border: "1px solid #ddd", padding: "8px" }}>Min Grade</th>
            <th style={{ border: "1px solid #ddd", padding: "8px" }}>Upload Resume</th>
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
                <td style={{ border: "1px solid #ddd", padding: "8px", textAlign: "center" }}>
                {application.cvApplication ? (
                    <div>
                    <span style={{ color: "green", fontWeight: "bold" }}>Resume uploaded</span>
                    <button
                        style={{ marginLeft: "10px" }}
                        onClick={() => downloadResume(application.id)}
                    >
                        Download
                    </button>
                    </div>
                ) : (
                    <input
                    type="file"
                    onChange={(event) => handleFileChange(event, application.id)} // Passing application.id
                    />
                )}
                </td>
            </tr>
            ))
        ) : (
            <tr>
            <td colSpan="5" style={{ textAlign: "center" }}>No open applications.</td>
            </tr>
        )}
        </tbody>
      </table>

      <div style={{ textAlign: "center", marginTop: "20px" }}>
        <button
          onClick={handleProfileClick}
          style={{
            padding: "10px 20px",
            backgroundColor: "#007bff",
            color: "#fff",
            border: "none",
            borderRadius: "5px",
            cursor: "pointer",
          }}
        >
          Profile
        </button>
      </div>
    </div>
  );
};

export default HomePage;
