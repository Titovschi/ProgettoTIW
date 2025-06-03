<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Benvenuto nella piattaforma eventi</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
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
        }

        header {
            background-color: white;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
            padding: 2.5rem 5%;
            position: relative;
            overflow: hidden;
        }

        header::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 4px;
            background: linear-gradient(90deg, #4CAF50, #8BC34A, #4CAF50);
            z-index: 1;
        }

        h1 {
            margin: 0 0 1rem 0;
            font-size: 2.5rem;
            font-weight: 700;
            color: #212529;
            position: relative;
            display: inline-block;
        }

        h1::after {
            content: '';
            position: absolute;
            width: 60px;
            height: 3px;
            background-color: #4CAF50;
            bottom: -8px;
            left: 0;
        }

        h2 {
            color: #212529;
            font-size: 1.8rem;
            margin: 2rem 0 1.5rem;
            font-weight: 600;
            position: relative;
            padding-left: 1rem;
        }

        h2::before {
            content: '';
            position: absolute;
            left: 0;
            top: 0;
            height: 100%;
            width: 4px;
            border-radius: 2px;
            background-color: #4CAF50;
        }

        header p {
            font-size: 1.1rem;
            color: #495057;
            margin-bottom: 1.5rem;
        }

        ul {
            list-style-type: none;
            padding: 0;
            display: flex;
            gap: 0.8rem;
            margin: 1.5rem 0;
        }

        ul li a {
            display: inline-block;
            text-decoration: none;
            padding: 0.75rem 1.5rem;
            background-color: #4CAF50;
            color: white;
            border-radius: 6px;
            font-weight: 500;
            transition: all 0.2s ease;
            box-shadow: 0 2px 8px rgba(76, 175, 80, 0.25);
        }

        ul li a:hover {
            background-color: #388e3c;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(76, 175, 80, 0.35);
        }

        main {
            flex: 1;
            max-width: 1200px;
            width: 90%;
            margin: 0 auto;
            padding: 2rem 0;
        }

        table {
            width: 100%;
            border-collapse: separate;
            border-spacing: 0;
            background-color: white;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
            margin: 1rem 0 2rem;
        }

        th, td {
            padding: 1rem;
            text-align: left;
        }

        th {
            background-color: #f8f9fa;
            font-weight: 600;
            color: #495057;
            text-transform: uppercase;
            font-size: 0.75rem;
            letter-spacing: 0.5px;
            border-bottom: 1px solid #e9ecef;
        }

        td {
            border-bottom: 1px solid #e9ecef;
            color: #495057;
            font-size: 0.95rem;
        }

        tr:last-child td {
            border-bottom: none;
        }

        tr:hover {
            background-color: rgba(236, 240, 241, 0.5);
        }

        .btn-iscrizione {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 0.6rem 1rem;
            border-radius: 6px;
            cursor: pointer;
            font-weight: 500;
            transition: all 0.2s ease;
            font-size: 0.85rem;
            box-shadow: 0 2px 5px rgba(76, 175, 80, 0.2);
        }

        .btn-iscrizione:hover {
            background-color: #388e3c;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(76, 175, 80, 0.3);
        }

        main > p {
            background-color: white;
            padding: 2rem;
            text-align: center;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
            color: #6c757d;
            font-size: 1.1rem;
        }

        footer {
            text-align: center;
            padding: 1.5rem;
            background-color: white;
            border-top: 1px solid #e9ecef;
            color: #6c757d;
            margin-top: auto;
        }

        footer p {
            margin: 0;
            font-size: 0.9rem;
        }

        /* Event Card Animations */
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        tr {
            animation: fadeIn 0.3s ease-out forwards;
        }

        tr:nth-child(2) { animation-delay: 0.05s; }
        tr:nth-child(3) { animation-delay: 0.1s; }
        tr:nth-child(4) { animation-delay: 0.15s; }
        tr:nth-child(5) { animation-delay: 0.2s; }

        /* Responsive Design */
        @media (max-width: 992px) {
            table {
                display: block;
                overflow-x: auto;
            }
        }

        @media (max-width: 768px) {
            header {
                padding: 1.5rem 5%;
            }

            h1 {
                font-size: 2rem;
            }

            h2 {
                font-size: 1.5rem;
            }
        }

        @media (max-width: 576px) {
            ul {
                flex-direction: column;
                gap: 0.5rem;
            }

            ul li a {
                display: block;
                text-align: center;
            }
        }
    </style>
</head>
<body>
<header>
    <h1>Benvenuto!</h1>
    <p>Seleziona un'opzione:</p>
    <ul>
        <li><a href="LoginServlet">Login</a></li>
        <li><a href="RegistrazioneServlet">Registrazione</a></li>
    </ul>
</header>

<main>
    <h2>Eventi Disponibili</h2>

    <c:if test="${empty eventiDisponibili}">
        <p>Nessun evento disponibile al momento.</p>
    </c:if>

    <c:if test="${not empty eventiDisponibili}">
        <table>
            <thead>
            <tr>
                <th>Nome</th>
                <th>Data</th>
                <th>Ora</th>
                <th>Luogo</th>
                <th>Descrizione</th>
                <th>Categoria</th>
                <th>Iscriviti</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="evento" items="${eventiDisponibili}">
                <tr>
                    <td>${evento.nome}</td>
                    <td>${evento.data}</td>
                    <td>${evento.ora}</td>
                    <td>${evento.luogo}</td>
                    <td>${evento.descrizione}</td>
                    <td>${evento.categoria.nome}</td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty sessionScope.user}">
                                <form action="IscrizioneServlet" method="post">
                                    <input type="hidden" name="eventoId" value="${evento.id}" />
                                    <button type="submit" class="btn-iscrizione">Iscriviti</button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <button class="btn-iscrizione" onclick="alert('Effettua il login per iscriverti!')">Iscriviti</button>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</main>

<footer>
    <p>&copy; Tito Catalano, studente di Ingegneria Digitale</p>
</footer>
</body>
</html>