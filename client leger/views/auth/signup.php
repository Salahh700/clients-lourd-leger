<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../public/css/global.css">
    <title>Inscription – Neige & Soleil</title>
    <style>
        body {
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background: linear-gradient(135deg, var(--light-ice) 0%, var(--light-sun) 100%);
            padding: 2rem 1rem;
        }

        .auth-card {
            background: white;
            border-radius: 16px;
            padding: 2.5rem;
            width: 100%;
            max-width: 480px;
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

        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 0.75rem;
        }

        .auth-label {
            display: block;
            font-weight: 600;
            font-size: 0.88rem;
            color: var(--dark-gray);
            margin-bottom: 0.4rem;
        }

        .form-group {
            margin-bottom: 1.1rem;
        }

        .auth-input {
            width: 100%;
            padding: 12px 14px;
            border: 1.5px solid #ddd;
            border-radius: 8px;
            font-size: 0.95rem;
            font-family: inherit;
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

        select.auth-input {
            appearance: none;
            background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='8' viewBox='0 0 12 8'%3E%3Cpath d='M1 1l5 5 5-5' stroke='%2395A5A6' stroke-width='1.5' fill='none' stroke-linecap='round'/%3E%3C/svg%3E");
            background-repeat: no-repeat;
            background-position: right 14px center;
            padding-right: 36px;
            cursor: pointer;
        }

        /* Indicateurs politique mot de passe */
        .password-rules {
            background: var(--light-gray);
            border-radius: 8px;
            padding: 0.85rem 1rem;
            margin-top: 0.5rem;
            margin-bottom: 1.1rem;
        }

        .password-rules-title {
            font-size: 0.82rem;
            font-weight: 600;
            color: var(--dark-gray);
            margin-bottom: 0.5rem;
        }

        .rule {
            display: flex;
            align-items: center;
            gap: 0.5rem;
            font-size: 0.85rem;
            color: var(--medium-gray);
            margin: 0.2rem 0;
            transition: color 0.2s;
        }

        .rule-icon {
            width: 16px;
            height: 16px;
            border-radius: 50%;
            border: 1.5px solid #dc3545;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 0.7rem;
            font-weight: 700;
            color: #dc3545;
            flex-shrink: 0;
            transition: all 0.2s;
        }

        .rule-icon::after { content: "✗"; }

        .rule.ok { color: #155724; }
        .rule.ok .rule-icon {
            background: #28a745;
            border-color: #28a745;
            color: white;
        }
        .rule.ok .rule-icon::after { content: "✓"; }

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
            margin-bottom: 0.75rem;
        }

        .auth-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 24px rgba(74, 144, 226, 0.35);
        }

        .auth-btn:disabled {
            background: #ccc;
            transform: none;
            box-shadow: none;
            cursor: not-allowed;
        }

        .auth-btn-secondary {
            width: 100%;
            padding: 12px;
            background: var(--light-gray);
            color: var(--dark-gray);
            border: none;
            border-radius: 8px;
            font-size: 0.95rem;
            font-weight: 600;
            cursor: pointer;
            font-family: inherit;
            transition: background 0.2s;
        }

        .auth-btn-secondary:hover { background: #ddd; }

        .auth-divider {
            border: none;
            border-top: 1px solid var(--light-gray);
            margin: 1.5rem 0;
        }

        .auth-footer {
            text-align: center;
            font-size: 0.92rem;
            color: var(--medium-gray);
        }

        .auth-footer a {
            color: var(--ice-blue);
            font-weight: 600;
        }
    </style>
</head>
<body>
    <div class="auth-card">

        <div class="auth-logo">
            <img src="../../public/images/logo.png" alt="Neige & Soleil">
        </div>

        <?php if (isset($_SESSION['error'])): ?>
            <p class="error">❌ <?= $_SESSION['error'] ?></p>
            <?php unset($_SESSION['error']); ?>
        <?php endif; ?>
        <?php if (isset($_SESSION['success'])): ?>
            <p class="success">✅ <?= $_SESSION['success'] ?></p>
            <?php unset($_SESSION['success']); ?>
        <?php endif; ?>

        <p class="auth-title">Créer un compte ✨</p>
        <p class="auth-subtitle">Rejoignez la communauté Neige & Soleil</p>

        <form action="../../controllers/auth/SignupController.php" method="post" id="signupForm">

            <div class="form-row">
                <div class="form-group">
                    <label class="auth-label">Prénom</label>
                    <input class="auth-input" type="text" name="prenom" placeholder="Jean" required>
                </div>
                <div class="form-group">
                    <label class="auth-label">Nom</label>
                    <input class="auth-input" type="text" name="nom" placeholder="Dupont" required>
                </div>
            </div>

            <div class="form-group">
                <label class="auth-label">Nom d'utilisateur</label>
                <input class="auth-input" type="text" name="username" placeholder="jean_dupont" required>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label class="auth-label">Ville</label>
                    <input class="auth-input" type="text" name="ville" placeholder="Paris" required>
                </div>
                <div class="form-group">
                    <label class="auth-label">Code postal</label>
                    <input class="auth-input" type="text" name="CodePostal" placeholder="75000" required>
                </div>
            </div>

            <div class="form-group">
                <label class="auth-label">Email</label>
                <input class="auth-input" type="email" name="mail" placeholder="jean@exemple.com" required>
            </div>

            <div class="form-group">
                <label class="auth-label">Mot de passe</label>
                <input class="auth-input" type="password" name="password" id="password" placeholder="••••••••" required autocomplete="new-password">

                <div class="password-rules">
                    <p class="password-rules-title">Le mot de passe doit contenir :</p>
                    <div class="rule" id="rule-length"><span class="rule-icon"></span> Au moins 8 caractères</div>
                    <div class="rule" id="rule-upper"><span class="rule-icon"></span> Une lettre majuscule</div>
                    <div class="rule" id="rule-lower"><span class="rule-icon"></span> Une lettre minuscule</div>
                    <div class="rule" id="rule-special"><span class="rule-icon"></span> Un caractère spécial (!@#$%...)</div>
                </div>
            </div>

            <div class="form-group">
                <label class="auth-label">Je suis un…</label>
                <select class="auth-input" name="role">
                    <option value="voyageur">🧳 Voyageur</option>
                    <option value="proprietaire">🏠 Propriétaire</option>
                </select>
            </div>

            <input class="auth-btn" type="submit" name="valider" value="Créer mon compte" id="submitBtn">
            <input class="auth-btn-secondary" type="reset" value="Réinitialiser">

        </form>

        <hr class="auth-divider">
        <p class="auth-footer">Déjà un compte ? <a href="login.php">Se connecter</a></p>
    </div>

    <script>
        const passwordInput = document.getElementById('password');
        const submitBtn     = document.getElementById('submitBtn');

        const rules = {
            'rule-length':  pwd => pwd.length >= 8,
            'rule-upper':   pwd => /[A-Z]/.test(pwd),
            'rule-lower':   pwd => /[a-z]/.test(pwd),
            'rule-special': pwd => /[^A-Za-z0-9]/.test(pwd),
        };

        function validate() {
            let allOk = true;
            for (const [id, test] of Object.entries(rules)) {
                const ok = test(passwordInput.value);
                document.getElementById(id).classList.toggle('ok', ok);
                if (!ok) allOk = false;
            }
            submitBtn.disabled = !allOk;
        }

        passwordInput.addEventListener('input', validate);
        validate();
    </script>
</body>
</html>
