<?php
session_start();
?>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../public/css/global.css">
    <title>Connexion – Neige & Soleil</title>
    <style>
        body {
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background: linear-gradient(135deg, var(--light-ice) 0%, var(--light-sun) 100%);
        }

        .auth-card {
            background: white;
            border-radius: 16px;
            padding: 2.5rem;
            width: 100%;
            max-width: 420px;
            box-shadow: 0 8px 32px rgba(46, 92, 138, 0.12);
        }

        .auth-logo {
            text-align: center;
            margin-bottom: 1.5rem;
        }

        .auth-logo img {
            height: 56px;
        }

        .auth-title {
            text-align: center;
            font-size: 1.5rem;
            font-weight: 700;
            color: var(--deep-ice);
            margin-bottom: 0.25rem;
        }

        .auth-subtitle {
            text-align: center;
            color: var(--medium-gray);
            font-size: 0.92rem;
            margin-bottom: 2rem;
        }

        .auth-label {
            display: block;
            font-weight: 600;
            font-size: 0.88rem;
            color: var(--dark-gray);
            margin-bottom: 0.4rem;
        }

        .auth-input {
            width: 100%;
            padding: 12px 14px;
            border: 1.5px solid #ddd;
            border-radius: 8px;
            font-size: 0.95rem;
            font-family: inherit;
            margin-bottom: 1.1rem;
            transition: border-color 0.3s, box-shadow 0.3s;
            box-sizing: border-box;
            background: #fafafa;
        }

        .auth-input:focus {
            outline: none;
            border-color: var(--ice-blue);
            background: white;
            box-shadow: 0 0 0 3px rgba(74, 144, 226, 0.12);
        }

        .auth-btn {
            width: 100%;
            padding: 13px;
            background: linear-gradient(135deg, var(--ice-blue), var(--deep-ice));
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 1rem;
            font-weight: 700;
            cursor: pointer;
            font-family: inherit;
            transition: all 0.3s ease;
            margin-top: 0.25rem;
        }

        .auth-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 24px rgba(74, 144, 226, 0.35);
        }

        .auth-footer {
            text-align: center;
            margin-top: 1.5rem;
            font-size: 0.92rem;
            color: var(--medium-gray);
        }

        .auth-footer a {
            color: var(--ice-blue);
            font-weight: 600;
        }

        .auth-divider {
            border: none;
            border-top: 1px solid var(--light-gray);
            margin: 1.5rem 0;
        }
    </style>
</head>
<body>
    <div class="auth-card">

        <div class="auth-logo">
            <img src="../../public/images/logo.png" alt="Neige & Soleil">
        </div>

        <?php if (isset($_SESSION['success'])): ?>
            <p class="success">✅ <?= $_SESSION['success'] ?></p>
            <?php unset($_SESSION['success']); ?>
        <?php endif; ?>
        <?php if (isset($_SESSION['error'])): ?>
            <p class="error">❌ <?= $_SESSION['error'] ?></p>
            <?php unset($_SESSION['error']); ?>
        <?php endif; ?>

        <p class="auth-title">Bon retour 👋</p>
        <p class="auth-subtitle">Connectez-vous à votre espace</p>

        <form action="../../controllers/auth/LoginController.php" method="POST">
            <label class="auth-label" for="username">Email ou nom d'utilisateur</label>
            <input class="auth-input" type="text" id="username" name="username" placeholder="exemple@mail.com" required>

            <label class="auth-label" for="password">Mot de passe</label>
            <input class="auth-input" type="password" id="password" name="password" placeholder="••••••••" required>

            <input class="auth-btn" type="submit" name="valider" value="Se connecter">
        </form>

        <hr class="auth-divider">

        <p class="auth-footer">Pas encore de compte ? <a href="signup.php">S'inscrire</a></p>
    </div>
</body>
</html>
