<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Modifica Evento</title>
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
      padding: 1.5rem 5%;
      position: relative;
      overflow: hidden;
      display: flex;
      justify-content: space-between;
      align-items: center;
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
      margin: 0;
      font-size: 1.8rem;
      font-weight: 600;
      color: #212529;
    }

    h2 {
      color: #212529;
      font-size: 1.8rem;
      margin: 0 0 1.5rem;
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

    main {
      flex: 1;
      max-width: 800px;
      width: 90%;
      margin: 2rem auto;
      padding: 2.5rem;
      background-color: white;
      border-radius: 10px;
      box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
      position: relative;
      overflow: hidden;
    }

    main::before {
      content: '';
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      height: 4px;
      background: linear-gradient(90deg, #4CAF50, #8BC34A, #4CAF50);
      z-index: 1;
    }

    form {
      display: grid;
      grid-template-columns: 1fr;
      gap: 1.5rem;
    }

    .form-group {
      display: flex;
      flex-direction: column;
    }

    label {
      font-weight: 500;
      color: #495057;
      margin-bottom: 0.5rem;
      font-size: 0.95rem;
    }

    input, select {
      padding: 0.8rem 1rem;
      border: 1px solid #ced4da;
      border-radius: 6px;
      font-size: 1rem;
      transition: border-color 0.2s, box-shadow 0.2s;
      font-family: inherit;
    }

    input:focus, select:focus {
      border-color: #4CAF50;
      outline: none;
      box-shadow: 0 0 0 3px rgba(76, 175, 80, 0.25);
    }

    input[type="number"] {
      -moz-appearance: textfield;
    }

    input[type="number"]::-webkit-outer-spin-button,
    input[type="number"]::-webkit-inner-spin-button {
      -webkit-appearance: none;
      margin: 0;
    }

    button[type="submit"] {
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
      margin-top: 1rem;
      text-transform: uppercase;
      letter-spacing: 0.5px;
      justify-self: start;
    }

    button[type="submit"]:hover {
      background-color: #388e3c;
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(76, 175, 80, 0.35);
    }

    .error-message {
      color: #dc3545;
      background-color: rgba(220, 53, 69, 0.1);
      padding: 0.75rem;
      border-radius: 6px;
      margin-bottom: 1rem;
      font-weight: 500;
      animation: fadeIn 0.3s ease-out forwards;
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

    .actions {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-top: 1.5rem;
    }

    .back-link {
      color: #6c757d;
      text-decoration: none;
      display: inline-flex;
      align-items: center;
      transition: color 0.2s;
      font-weight: 500;
    }

    .back-link:hover {
      color: #4CAF50;
    }

    .back-link::before {
      content: '←';
      margin-right: 0.5rem;
    }

    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(10px); }
      to { opacity: 1; transform: translateY(0); }
    }

    main {
      animation: fadeIn 0.4s ease-out forwards;
    }

    @media (max-width: 768px) {
      main {
        padding: 1.5rem;
      }
    }
  </style>
</head>
<body>
<header>
  <h1>Piattaforma Eventi</h1>
</header>

<main>
  <h2>Modifica Evento</h2>

  <c:if test="${not empty erroreData}">
    <div class="error-message">
        ${erroreData}
    </div>
  </c:if>

  <form method="post" action="modificaEvento">
    <input type="hidden" name="id" value="${evento.id}" />

    <div class="form-group">
      <label for="nome">Nome:</label>
      <input type="text" id="nome" name="nome" value="${evento.nome}" required/>
    </div>

    <div class="form-group">
      <label for="descrizione">Descrizione:</label>
      <input type="text" id="descrizione" name="descrizione" value="${evento.descrizione}" required/>
    </div>

    <div class="form-group">
      <label for="luogo">Luogo:</label>
      <input type="text" id="luogo" name="luogo" value="${evento.luogo}" required/>
    </div>

    <div class="form-group">
      <label for="data">Data:</label>
      <input type="date" id="data" name="data" value="${evento.data}" required/>
    </div>

    <div class="form-group">
      <label for="ora">Ora:</label>
      <input type="time" id="ora" name="ora" value="${evento.ora}" required/>
    </div>

    <div class="form-group">
      <label for="capacita">Capacità:</label>
      <input type="number" id="capacita" name="capacita" min="1" value="${evento.capacita}" required/>
    </div>

    <div class="form-group">
      <label for="categoriaId">Categoria:</label>
      <select id="categoriaId" name="categoriaId">
        <c:forEach var="cat" items="${categorie}">
          <option value="${cat.id}" ${cat.id == evento.categoria.id ? 'selected' : ''}>${cat.nome}</option>
        </c:forEach>
      </select>
    </div>

    <div class="actions">
      <a href="${pageContext.request.contextPath}/dashboard" class="back-link">Torna alla Dashboard</a>
      <button type="submit">Salva</button>
    </div>
  </form>
</main>

<footer>
  <p>&copy; Tito Catalano, studente di Ingegneria Digitale</p>
</footer>
</body>
</html>