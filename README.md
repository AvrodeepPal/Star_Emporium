# Star Emporium

**Star Emporium** is a state-of-the-art ebook store management software designed for readers who prioritize eco-consciousness and affordability. Built using **Java Swing GUI** for a smooth and intuitive interface and **MySQL** (via JDBC) for secure and reliable backend operations, this application provides a robust, seamless, and secure experience for users.

---

## Features

### Functional Features:
- **User Registration and Authentication**  
  Secure login and account creation with robust password policies.
  
- **Emporium Management**  
  A comprehensive database of ebooks categorized by title, author, genre, and keywords.

- **Book Search and Filtering**  
  Advanced search and filtering options for locating ebooks with ease.

- **Shopping Cart Management**  
  Easily add, update, and remove ebooks, with automatic price calculations.

- **Secure Payment Processing**  
  Integration with secure payment gateways for hassle-free transactions.

- **User Profiles**  
  Manage personal information, purchase history, and access book recommendations.

- **Online E-Book Reading**  
  Features include bookmarking, highlighting, and text customization for a personalized reading experience.

### Non-Functional Features:
- **Smooth Performance**  
  Optimized for fast and reliable operations, ensuring quick response times.

- **Scalable Architecture**  
  Designed to handle growing user traffic efficiently.

- **Secure Authentication and Privacy**  
  Implements robust data security measures to safeguard user credentials and transactions.

- **Cross-Platform Compatibility**  
  Built with Java Swing, enabling platform-independent deployment.

- **Eco-Friendly Solution**  
  Reduces paper usage and promotes sustainability through digital reading.

---

## Technical Overview

### Software Requirements:
- **Frontend**: Java Swing GUI (NetBeans IDE)
- **Backend**: MySQL (via JDBC)

### Hardware Requirements:
- **Operating System**: Windows 7 SP1 or newer (64-bit)
- **Processor**: Intel Core i3-2100 or newer
- **RAM**: Minimum 4 GB
- **Storage**: 10 GB (recommended)

### Database Design:
- **Key Tables**:
  - `usrlgn`: Stores user credentials.
  - `userprofile`: Contains user profile data.
  - `empo`: Stores ebook details.
  - `carts`: Tracks books added to user carts.
  - `allorders`: Maintains order history.

---

## Installation and Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/AvrodeepPal/Star_Emporium.git
   ```
2. Install [Java JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) and [MySQL Server](https://dev.mysql.com/downloads/installer/).
3. Open the project in [NetBeans IDE](https://netbeans.apache.org/).
4. Configure the MySQL database:
   - Import the provided schema and table definitions from `database/Emporium.sql`.
   - Update database credentials in the codebase.
5. Run the project via NetBeans.

---

## Future Enhancements

- **AI & ML Integration**: Personalized book recommendations and analytics.
- **Blockchain for Security**: Enhanced transaction security and book ownership tracking.
- **Third-Party Ecosystem Integration**: Expand reach through external platforms.
- **Global Market Optimization**: Regional trend analysis for international users.

---

## About the Developers

**Contributors:**
- Avrodeep Pal  
  [LinkedIn](https://linkedin.com/in/AvrodeepPal) | [GitHub](https://github.com/AvrodeepPal)
- Indrasish Biswas  
- Snehargha Mukherjee  
- Tamonash Sarkar  

**Guiding Principle:**  
*"Once you learn to read, you will be forever free."* – Frederick Douglass

**Contact**: [avrodeep.pal17@gmail.com](mailto:avrodeep.pal17@gmail.com)

---

## License

This project is licensed under the [MIT License](LICENSE).

---

Enjoy reading with **Star Emporium** – your gateway to eco-conscious digital books!
