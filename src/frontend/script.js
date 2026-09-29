const API = "http://localhost:8081";

async function submitComplaint() {
    const name = document.getElementById("name").value.trim();
    const category = document.getElementById("type").value.trim();
    const description = document.getElementById("description").value.trim();

    if (!name || !category || !description) {
        alert("Please fill all fields.");
        return;
    }

    const data = new URLSearchParams({ name, category, description });

    try {
        const response = await fetch(`${API}/submit`, {
            method: "POST",
            body: data
        });
        const result = await response.json();
        if (!response.ok) throw new Error(result.error || "Submission failed");

        alert(`Complaint #${result.id} submitted successfully.`);
        document.getElementById("name").value = "";
        document.getElementById("type").value = "";
        document.getElementById("description").value = "";
        loadComplaints();
    } catch (error) {
        alert("Java backend is not running. Start Main.java first.");
    }
}

async function loadComplaints() {
    const list = document.getElementById("complaintList");

    try {
        const response = await fetch(`${API}/complaints`);
        const complaints = await response.json();

        if (!complaints.length) {
            list.innerHTML = "<p>No complaints in queue.</p>";
            return;
        }

        list.innerHTML = complaints.map((c, index) => `
            <div class="complaint">
                <b>Queue Position: ${index + 1}</b><br>
                Complaint #${c.id}<br>
                Student: ${escapeHtml(c.name)}<br>
                Category: ${escapeHtml(c.category)}<br>
                Description: ${escapeHtml(c.description)}<br>
                Status: <b>${escapeHtml(c.status)}</b>
            </div>
        `).join("");
    } catch (error) {
        list.innerHTML = "<p>Connect to the Java backend to view the queue.</p>";
    }
}

async function processNextComplaint() {
    try {
        const response = await fetch(`${API}/process`, { method: "POST" });
        const result = await response.json();
        if (!response.ok) throw new Error(result.error || "No complaint available");

        alert(`Complaint #${result.id} processed.`);
        loadComplaints();
    } catch (error) {
        alert(error.message);
    }
}

function escapeHtml(value) {
    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}

loadComplaints();
setInterval(loadComplaints, 2000);
