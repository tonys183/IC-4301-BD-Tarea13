-- Users
CREATE TABLE Users (
    user_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

-- Books
CREATE TABLE Books (
    book_id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(100) NOT NULL,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    available_copies INTEGER NOT NULL DEFAULT 0,
    total_copies INTEGER NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE'
);

-- Loans
CREATE TABLE Loans (
    loan_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    loan_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expected_return_date TIMESTAMP NOT NULL,
    actual_return_date TIMESTAMP,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (book_id) REFERENCES Books(book_id)
);

-- Notifications
CREATE TABLE Notifications (
    notification_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    loan_id INT,
    message TEXT NOT NULL,
    sent_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) NOT NULL DEFAULT 'SENT',
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (loan_id) REFERENCES Loans(loan_id)
);