<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrazione</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap">
    <style>
        body {
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
            color: #212529;
            line-height: 1.6;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }

        .registration-container {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
            width: 100%;
            max-width: 420px;
            padding: 2.5rem;
            position: relative;
            overflow: hidden;
        }

        .registration-container::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 4px;
            background: linear-gradient(90deg, #4CAF50, #8BC34A, #4CAF50);
            z-index: 1;
        }

        h2 {
            margin-top: 0;
            margin-bottom: 1.5rem;
            font-size: 1.8rem;
            font-weight: 600;
            color: #212529;
            position: relative;
            display: inline-block;
        }

        h2::after {
            content: '';
            position: absolute;
            width: 40px;
            height: 3px;
            background-color: #4CAF50;
            bottom: -8px;
            left: 0;
        }

        form {
            margin-top: 1.5rem;
        }

        label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 500;
            color: #495057;
            font-size: 0.95rem;
        }

        input[type="text"],
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 0.8rem 1rem;
            margin-bottom: 1.5rem;
            border: 1px solid #ced4da;
            border-radius: 6px;
            font-size: 1rem;
            transition: border-color 0.2s, box-shadow 0.2s;
            box-sizing: border-box;
        }

        input[type="text"]:focus,
        input[type="email"]:focus,
        input[type="password"]:focus {
            border-color: #4CAF50;
            outline: none;
            box-shadow: 0 0 0 3px rgba(76, 175, 80, 0.25);
        }

        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 0.8rem 1.5rem;
            border-radius: 6px;
            cursor: pointer;
            font-weight: 500;
            transition: all 0.2s ease;
            font-size: 1rem;
            box-shadow: 0 2px 8px rgba(76, 175, 80, 0.25);
            width: 100%;
            margin-top: 0.5rem;
        }

        input[type="submit"]:hover {
            background-color: #388e3c;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(76, 175, 80, 0.35);
        }

        p {
            margin-top: 1.5rem;
            text-align: center;
            color: #6c757d;
        }

        p a {
            color: #4CAF50;
            text-decoration: none;
            font-weight: 500;
            transition: color 0.2s;
        }

        p a:hover {
            color: #388e3c;
            text-decoration: underline;
        }

        p[style*="color:red"] {
            color: #dc3545 !important;
            background-color: rgba(220, 53, 69, 0.1);
            padding: 0.75rem;
            border-radius: 6px;
            margin-top: 1rem;
            text-align: center;
            font-size: 0.95rem;
        }

        .back-home {
            display: inline-block;
            margin-top: 2rem;
            color: #6c757d;
            text-decoration: none;
            font-size: 0.9rem;
            transition: color 0.2s;
        }

        .back-home:hover {
            color: #4CAF50;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .registration-container {
            animation: fadeIn 0.4s ease-out forwards;
        }

        @media (max-width: 576px) {
            .registration-container {
                width: 85%;
                padding: 2rem;
            }
        }
    </style>
</head>
<body>
<div class="registration-container">
    <h2>Registrazione</h2>

    <form action="RegistrazioneServlet" method="post">
        <label>Username:</label>
        <input type="text" name="username" required>

        <label>Email:</label>
        <input type="email" name="email" required>

        <label>Password:</label>
        <input type="password" name="password" required>

        <input type="submit" value="Registrati">
    </form>

    <% String msg = (String) request.getAttribute("msg"); %>
    <% if (msg != null) { %>
    <p style="color:red;"><%= msg %></p>
    <% } %>

    <p>Hai già un account? <a href="LoginServlet">Accedi</a></p>
</div>

<a href="${pageContext.request.contextPath}/" class="back-home">← Torna alla Home</a>
</body>
</html>