# CampusFix - Student Complaint Management System

**CampusFix** is a Java-based Minor Project developed to manage student complaints in a college campus.

Students can submit complaints through a simple web interface and check their complaint status. The application connects the HTML/CSS/JavaScript frontend with a Java HTTP backend using a Queue data structure and FIFO (First In, First Out) order.

---

## 📌 Project Overview

- **Project Type:** Minor Project
- **Domain:** Data Structures & Algorithms
- **Application Type:** Java Console + Web Frontend
- **Main Data Structure:** Queue (FIFO)

---

## 👥 Team Members

- **Arjun Saha**
- **Vani Chandra**
- **Rahul Pandey**
- **Leena Chandrakar**
- **Shashi Shekhar Patel**

---

## 🚀 Key Features

- **Student Module:** Students can submit complaints and check their status.
- **Admin Module:** Admin can review and process complaints.
- **FIFO Queue Logic:** The first submitted complaint is processed first.
- **Web Frontend:** Students can submit complaints through HTML, CSS and JavaScript.
- **Java API:** `ApiServer.java` connects the web frontend with the Java application.
- **Complaint Status:** Complaints start as Pending and can be marked as Resolved.

---

## 🛠️ Technologies Used

- **Programming Language:** Java
- **Data Structure:** Queue
- **Implementation:** LinkedList
- **Frontend:** HTML5, CSS3, JavaScript
- **Backend / API Server:** Java HTTP Server (`ApiServer.java`)
- **IDE:** IntelliJ IDEA

---

## 🧠 Key Java & DSA Concepts Applied

- **OOP:** Classes, Objects, Constructors and Encapsulation
- **Collections Framework:** Queue and LinkedList
- **Generics:** `Queue<Complaint>` ensures that the queue stores Complaint objects.
- **FIFO:** First In, First Out complaint processing
- **Scanner:** Used for console input
- **HTTP Handling:** Connects frontend requests with the Java backend

---

## 🔌 API Endpoints

- `POST /submit` - Submits a new complaint into the queue.
- `GET /complaints` - Retrieves the current complaints.
- `POST /process` - Processes the next complaint using `poll()`.
- `GET /next` - Gets the next complaint in the queue.

---

## 📂 Project Structure

```text
src/
├── frontend/
│   ├── index.html
│   ├── script.js
│   └── style.css
│
├── main/
│   ├── Main.java
│   └── ApiServer.java
│
├── model/
│   └── Complaint.java
│
└── service/
    ├── Student.java
    └── Admin.java
