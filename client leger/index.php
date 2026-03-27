<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="public/css/global.css">
    <title>Neige & Soleil – Location de logements</title>
    <style>
        /* ========================================
           NAVBAR INDEX
        ======================================== */
        .index-nav {
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            z-index: 1000;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0 2.5rem;
            height: 72px;
            background: rgba(255,255,255,0.92);
            backdrop-filter: blur(10px);
            box-shadow: 0 2px 12px rgba(0,0,0,0.08);
        }

        .index-nav .logo { height: 44px; }

        .index-nav-links {
            display: flex;
            gap: 0.5rem;
        }

        .nav-btn {
            padding: 10px 22px;
            border-radius: 8px;
            font-weight: 600;
            font-size: 0.95rem;
            text-decoration: none;
            transition: all 0.3s ease;
        }

        .nav-btn-outline {
            border: 2px solid var(--ice-blue);
            color: var(--ice-blue);
        }

        .nav-btn-outline:hover {
            background: var(--ice-blue);
            color: white;
            text-decoration: none;
        }

        .nav-btn-fill {
            background: linear-gradient(135deg, var(--ice-blue), var(--deep-ice));
            color: white;
            border: 2px solid transparent;
        }

        .nav-btn-fill:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(74,144,226,0.35);
            color: white;
            text-decoration: none;
        }

        /* ========================================
           HERO
        ======================================== */
        .hero {
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            text-align: center;
            padding: 6rem 2rem 4rem;
            background:
                linear-gradient(135deg,
                    rgba(74,144,226,0.12) 0%,
                    rgba(255,107,53,0.08) 100%),
                radial-gradient(ellipse at 20% 50%, rgba(74,144,226,0.15) 0%, transparent 60%),
                radial-gradient(ellipse at 80% 50%, rgba(255,107,53,0.12) 0%, transparent 60%),
                var(--light-gray);
        }

        .hero-content { max-width: 720px; }

        .hero-badge {
            display: inline-block;
            padding: 6px 18px;
            background: var(--light-ice);
            color: var(--deep-ice);
            border: 1.5px solid var(--ice-shadow);
            border-radius: 20px;
            font-size: 0.88rem;
            font-weight: 600;
            margin-bottom: 1.5rem;
            letter-spacing: 0.3px;
        }

        .hero h1 {
            font-size: clamp(2.2rem, 5vw, 3.5rem);
            font-weight: 800;
            line-height: 1.15;
            margin-bottom: 1.25rem;
            background: linear-gradient(135deg, var(--ice-blue) 0%, var(--sun-orange) 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
        }

        .hero p {
            font-size: 1.15rem;
            color: var(--medium-gray);
            line-height: 1.7;
            margin-bottom: 2.5rem;
            max-width: 560px;
            margin-left: auto;
            margin-right: auto;
        }

        .hero-cta {
            display: flex;
            gap: 1rem;
            justify-content: center;
            flex-wrap: wrap;
        }

        .cta-primary {
            display: inline-block;
            padding: 15px 36px;
            background: linear-gradient(135deg, var(--ice-blue), var(--deep-ice));
            color: white;
            border-radius: 10px;
            font-weight: 700;
            font-size: 1.05rem;
            text-decoration: none;
            transition: all 0.3s ease;
            box-shadow: 0 4px 16px rgba(74,144,226,0.3);
        }

        .cta-primary:hover {
            transform: translateY(-3px);
            box-shadow: 0 10px 28px rgba(74,144,226,0.4);
            color: white;
            text-decoration: none;
        }

        .cta-secondary {
            display: inline-block;
            padding: 15px 36px;
            background: white;
            color: var(--dark-gray);
            border-radius: 10px;
            font-weight: 700;
            font-size: 1.05rem;
            text-decoration: none;
            border: 2px solid #e0e0e0;
            transition: all 0.3s ease;
        }

        .cta-secondary:hover {
            border-color: var(--ice-blue);
            color: var(--ice-blue);
            text-decoration: none;
            transform: translateY(-2px);
        }

        /* ========================================
           STATS
        ======================================== */
        .stats-bar {
            background: white;
            border-top: 1px solid #eee;
            border-bottom: 1px solid #eee;
            padding: 2rem;
            display: flex;
            justify-content: center;
            gap: 4rem;
            flex-wrap: wrap;
        }

        .stat-item { text-align: center; }

        .stat-number {
            font-size: 2rem;
            font-weight: 800;
            color: var(--deep-ice);
            display: block;
        }

        .stat-label {
            font-size: 0.88rem;
            color: var(--medium-gray);
            font-weight: 500;
        }

        /* ========================================
           FONCTIONNEMENT
        ======================================== */
        .section {
            padding: 5rem 2rem;
        }

        .section-title {
            text-align: center;
            font-size: clamp(1.6rem, 3vw, 2.2rem);
            font-weight: 800;
            color: var(--dark-gray);
            margin-bottom: 0.75rem;
        }

        .section-subtitle {
            text-align: center;
            color: var(--medium-gray);
            font-size: 1rem;
            margin-bottom: 3rem;
            max-width: 500px;
            margin-left: auto;
            margin-right: auto;
        }

        .steps-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
            gap: 2rem;
            max-width: 1000px;
            margin: 0 auto;
        }

        .step-card {
            background: white;
            border-radius: 14px;
            padding: 2rem 1.5rem;
            text-align: center;
            box-shadow: 0 2px 12px rgba(0,0,0,0.07);
            transition: transform 0.3s, box-shadow 0.3s;
        }

        .step-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 24px rgba(0,0,0,0.1);
        }

        .step-icon {
            font-size: 2.5rem;
            margin-bottom: 1rem;
            display: block;
        }

        .step-num {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            width: 28px;
            height: 28px;
            background: var(--ice-blue);
            color: white;
            border-radius: 50%;
            font-size: 0.8rem;
            font-weight: 700;
            margin-bottom: 0.75rem;
        }

        .step-card h4 {
            font-size: 1.05rem;
            color: var(--dark-gray);
            margin-bottom: 0.5rem;
        }

        .step-card p {
            font-size: 0.9rem;
            color: var(--medium-gray);
            line-height: 1.6;
            margin: 0;
        }

        /* ========================================
           POURQUOI NOUS
        ======================================== */
        .why-section {
            background: white;
            padding: 5rem 2rem;
        }

        .why-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
            gap: 2rem;
            max-width: 1000px;
            margin: 0 auto;
        }

        .why-item {
            display: flex;
            gap: 1rem;
            align-items: flex-start;
        }

        .why-icon {
            font-size: 1.8rem;
            flex-shrink: 0;
            margin-top: 0.1rem;
        }

        .why-item h4 {
            font-size: 1rem;
            color: var(--dark-gray);
            margin-bottom: 0.3rem;
        }

        .why-item p {
            font-size: 0.88rem;
            color: var(--medium-gray);
            line-height: 1.6;
            margin: 0;
        }

        /* ========================================
           CTA BOTTOM
        ======================================== */
        .cta-section {
            padding: 5rem 2rem;
            background: linear-gradient(135deg, var(--deep-ice) 0%, var(--ice-blue) 60%, var(--sun-orange) 100%);
            text-align: center;
        }

        .cta-section h2 {
            font-size: clamp(1.7rem, 3vw, 2.4rem);
            color: white;
            margin-bottom: 0.75rem;
            -webkit-text-fill-color: white;
        }

        .cta-section p {
            color: rgba(255,255,255,0.82);
            font-size: 1.05rem;
            margin-bottom: 2rem;
        }

        .cta-white {
            display: inline-block;
            padding: 14px 36px;
            background: white;
            color: var(--deep-ice);
            border-radius: 10px;
            font-weight: 700;
            font-size: 1rem;
            text-decoration: none;
            transition: all 0.3s ease;
            margin: 0.4rem;
        }

        .cta-white:hover {
            transform: translateY(-3px);
            box-shadow: 0 10px 28px rgba(0,0,0,0.2);
            color: var(--deep-ice);
            text-decoration: none;
        }

        .cta-white-outline {
            display: inline-block;
            padding: 14px 36px;
            background: transparent;
            color: white;
            border: 2px solid rgba(255,255,255,0.7);
            border-radius: 10px;
            font-weight: 700;
            font-size: 1rem;
            text-decoration: none;
            transition: all 0.3s ease;
            margin: 0.4rem;
        }

        .cta-white-outline:hover {
            background: rgba(255,255,255,0.15);
            border-color: white;
            color: white;
            text-decoration: none;
        }

        /* ========================================
           FOOTER
        ======================================== */
        .index-footer {
            background: var(--dark-gray);
            color: #aaa;
            text-align: center;
            padding: 2rem;
            font-size: 0.88rem;
        }

        .index-footer a { color: var(--sun-orange); }

        /* ========================================
           RESPONSIVE
        ======================================== */
        @media (max-width: 600px) {
            .index-nav { padding: 0 1.25rem; }
            .stats-bar { gap: 2rem; }
            .hero-cta { flex-direction: column; align-items: center; }
        }
    </style>
</head>
<body>

    <!-- NAVBAR -->
    <nav class="index-nav">
        <img class="logo" src="public/images/logo.png" alt="Neige & Soleil">
        <div class="index-nav-links">
            <a href="views/auth/login.php"  class="nav-btn nav-btn-outline">Se connecter</a>
            <a href="views/auth/signup.php" class="nav-btn nav-btn-fill">S'inscrire</a>
        </div>
    </nav>

    <!-- HERO -->
    <section class="hero">
        <div class="hero-content">
            <span class="hero-badge">❄️ Neige &amp; ☀️ Soleil</span>
            <h1>Trouvez le logement parfait pour votre prochaine escapade</h1>
            <p>Des centaines de logements à la montagne et au soleil, réservés directement auprès de propriétaires de confiance.</p>
            <div class="hero-cta">
                <a href="views/auth/signup.php" class="cta-primary">Commencer gratuitement</a>
                <a href="views/auth/login.php"  class="cta-secondary">J'ai déjà un compte</a>
            </div>
        </div>
    </section>

    <!-- STATS -->
    <div class="stats-bar">
        <div class="stat-item">
            <span class="stat-number">500+</span>
            <span class="stat-label">Logements disponibles</span>
        </div>
        <div class="stat-item">
            <span class="stat-number">1 200+</span>
            <span class="stat-label">Voyageurs satisfaits</span>
        </div>
        <div class="stat-item">
            <span class="stat-number">300+</span>
            <span class="stat-label">Propriétaires actifs</span>
        </div>
        <div class="stat-item">
            <span class="stat-number">50 km</span>
            <span class="stat-label">Rayon de recherche</span>
        </div>
    </div>

    <!-- COMMENT ÇA MARCHE -->
    <section class="section">
        <h2 class="section-title">Comment ça marche ?</h2>
        <p class="section-subtitle">Réservez un logement en quelques étapes simples.</p>

        <div class="steps-grid">
            <div class="step-card">
                <span class="step-icon">📝</span>
                <span class="step-num">1</span>
                <h4>Créez votre compte</h4>
                <p>Inscrivez-vous en tant que voyageur ou propriétaire en quelques secondes.</p>
            </div>
            <div class="step-card">
                <span class="step-icon">🔍</span>
                <span class="step-num">2</span>
                <h4>Trouvez un logement</h4>
                <p>Recherchez par ville, nom ou code postal. Les logements proches de chez vous apparaissent automatiquement.</p>
            </div>
            <div class="step-card">
                <span class="step-icon">📅</span>
                <span class="step-num">3</span>
                <h4>Choisissez vos dates</h4>
                <p>Sélectionnez vos dates et voyez le prix total ajusté selon la saison.</p>
            </div>
            <div class="step-card">
                <span class="step-icon">🎉</span>
                <span class="step-num">4</span>
                <h4>Profitez !</h4>
                <p>Recevez la confirmation et les infos d'accès directement du propriétaire.</p>
            </div>
        </div>
    </section>

    <!-- POURQUOI NOUS -->
    <section class="why-section">
        <h2 class="section-title">Pourquoi choisir Neige & Soleil ?</h2>
        <p class="section-subtitle">Une plateforme pensée pour les voyageurs et les propriétaires.</p>

        <div class="why-grid">
            <div class="why-item">
                <span class="why-icon">📍</span>
                <div>
                    <h4>Recherche géolocalisée</h4>
                    <p>Trouvez les logements les plus proches de votre position automatiquement.</p>
                </div>
            </div>
            <div class="why-item">
                <span class="why-icon">💰</span>
                <div>
                    <h4>Tarifs saisonniers transparents</h4>
                    <p>Prix ajustés selon la saison, affichés clairement avant de réserver.</p>
                </div>
            </div>
            <div class="why-item">
                <span class="why-icon">❤️</span>
                <div>
                    <h4>Logements favoris</h4>
                    <p>Sauvegardez les logements qui vous plaisent pour les retrouver facilement.</p>
                </div>
            </div>
            <div class="why-item">
                <span class="why-icon">🏠</span>
                <div>
                    <h4>Espace propriétaire</h4>
                    <p>Gérez vos logements et suivez vos réservations depuis un tableau de bord dédié.</p>
                </div>
            </div>
            <div class="why-item">
                <span class="why-icon">🔒</span>
                <div>
                    <h4>Comptes sécurisés</h4>
                    <p>Politique de mots de passe stricte et données protégées.</p>
                </div>
            </div>
            <div class="why-item">
                <span class="why-icon">⚡</span>
                <div>
                    <h4>Réservation instantanée</h4>
                    <p>Disponibilités vérifiées en temps réel, confirmation immédiate.</p>
                </div>
            </div>
        </div>
    </section>

    <!-- CTA FINAL -->
    <section class="cta-section">
        <h2>Prêt à partir ?</h2>
        <p>Rejoignez des milliers de voyageurs et propriétaires sur Neige & Soleil.</p>
        <a href="views/auth/signup.php" class="cta-white">Créer un compte gratuit</a>
        <a href="views/auth/login.php"  class="cta-white-outline">Se connecter</a>
    </section>

    <!-- FOOTER -->
    <footer class="index-footer">
        <p>© 2026 Neige & Soleil — Tous droits réservés</p>
    </footer>

</body>
</html>
