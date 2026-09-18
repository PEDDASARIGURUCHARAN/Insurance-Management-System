import React from 'react';
import { useNavigate } from 'react-router-dom';

export default function HomePage() {
  const navigate = useNavigate();

  const scrollToSection = (id) => {
    const element = document.getElementById(id);
    if (element) {
      element.scrollIntoView({ behavior: 'smooth' });
    }
  };

  return (
    <div className="home-container">
      {/* NAVBAR */}
      <nav className="home-navbar">
        <div className="brand-logo">
          <img src="/agile-insurance-logo.png" alt="Agile Insurance Logo" style={{ height: '80px', width: 'auto' }} />
          <div>
            AEGIS <span className="highlight">ASSURANCE</span> GROUP
          </div>
        </div>

        <ul className="nav-links">
          <li><button onClick={() => window.scrollTo({ top: 0, behavior: 'smooth' })} className="nav-link">Home</button></li>
          <li><button onClick={() => scrollToSection('about')} className="nav-link">About</button></li>
          <li><button onClick={() => scrollToSection('services')} className="nav-link">Services</button></li>
          <li><button onClick={() => scrollToSection('contact')} className="nav-link">Contact</button></li>
          <li>
            <button
              id="nav-login-btn"
              onClick={() => navigate('/login')}
              className="btn btn-primary btn-sm"
            >
              Portal Login
            </button>
          </li>
        </ul>
      </nav>

      {/* HERO SECTION */}
      <header className="hero-section">
        <div className="hero-inner">
          <div className="hero-content">
            <div className="hero-tag">
              <span>🛡️</span> Institutional Grade Risk Protection
            </div>
            <h1 className="hero-title">
              Safeguarding Tomorrow with <span>Aegis Assurance</span>
            </h1>
            <p className="hero-desc">
              Comprehensive personal, enterprise, and health coverage engineered with decades of underwriting excellence, digital policy management, and rapid claims settlement.
            </p>
            <div className="hero-actions">
              <button
                id="hero-portal-login-btn"
                onClick={() => navigate('/login')}
                className="btn btn-primary"
                style={{ backgroundColor: '#2563eb', borderColor: '#2563eb' }}
              >
                Access System Portal →
              </button>
              <button
                onClick={() => scrollToSection('services')}
                className="btn btn-secondary"
              >
                Explore Coverage Plans
              </button>
            </div>
          </div>

          <div className="hero-stats-card">
            <h3>Enterprise Solvency & Performance</h3>
            <div className="stats-grid">
              <div className="stat-box">
                <div className="stat-num">99.4%</div>
                <div className="stat-lbl">Claims Settlement Rate</div>
              </div>
              <div className="stat-box">
                <div className="stat-num">$4.8B+</div>
                <div className="stat-lbl">Assets Protected</div>
              </div>
              <div className="stat-box">
                <div className="stat-num">1.2M+</div>
                <div className="stat-lbl">Active Policyholders</div>
              </div>
              <div className="stat-box">
                <div className="stat-num">24/7</div>
                <div className="stat-lbl">Emergency Response</div>
              </div>
            </div>
          </div>
        </div>
      </header>

      {/* ABOUT SECTION */}
      <section id="about" className="section-wrapper">
        <div className="section-header">
          <div className="section-tag">About Aegis</div>
          <h2 className="section-title">Centuries of Financial Steadfastness</h2>
          <p className="section-subtitle">
            Aegis Assurance Group delivers peace of mind through institutional strength, rigorous underwriting, and empathetic customer-first claim resolution.
          </p>
        </div>

        <div className="about-grid">
          <div className="about-card">
            <h4>Direct Policyholder Focus</h4>
            <p>
              We prioritize transparency in coverage language, competitive actuarial premiums, and an intuitive unified portal for real-time claim adjudication.
            </p>
          </div>
          <div className="about-card">
            <h4>Enterprise-Grade Security & Integrity</h4>
            <p>
              Our automated claim verification pipelines safeguard funds against illegitimate filings, speeding up genuine settlements for our valued policyholders.
            </p>
          </div>
          <div className="about-card">
            <h4>A+ Financial Strength Rating</h4>
            <p>
              Recognized by top credit and insurance rating bureaus for exemplary capital adequacy, liquidity reserves, and continuous risk diversification.
            </p>
          </div>
          <div className="about-card">
            <h4>Fast-Track Digital Adjudication</h4>
            <p>
              Direct digital filing, instant document verification, and dedicated underwriter reviews ensure eligible claims are processed in record turnaround times.
            </p>
          </div>
        </div>
      </section>

      {/* SERVICES SECTION */}
      <section id="services" style={{ backgroundColor: '#f1f5f9' }}>
        <div className="section-wrapper">
          <div className="section-header">
            <div className="section-tag">Coverage Solutions</div>
            <h2 className="section-title">Engineered For Total Resilience</h2>
            <p className="section-subtitle">
              Tailored insurance contracts for individuals, families, and commercial enterprises.
            </p>
          </div>

          <div className="services-grid">
            <div className="service-card">
              <div className="service-icon">🏥</div>
              <h3>Comprehensive Health Shield</h3>
              <p>
                Inpatient and outpatient healthcare protection with cashless hospital admission across over 12,000 certified healthcare facilities.
              </p>
              <ul className="service-features">
                <li>Pre and post hospitalization care</li>
                <li>Prescription and wellness subsidies</li>
                <li>Zero-wait critical illness cover</li>
              </ul>
            </div>

            <div className="service-card">
              <div className="service-icon">🚗</div>
              <h3>Executive Motor Guard</h3>
              <p>
                All-risk vehicle collision, third-party liability, zero-depreciation repair, and 24/7 roadside emergency dispatch.
              </p>
              <ul className="service-features">
                <li>On-the-spot telematics claims filing</li>
                <li>Guaranteed OEM parts replacement</li>
                <li>Complimentary loaner vehicle</li>
              </ul>
            </div>

            <div className="service-card">
              <div className="service-icon">🏡</div>
              <h3>Prestige Home & Property</h3>
              <p>
                Protection against fire, structural damages, storm impact, theft, and water intrusion with full structural restoration indemnity.
              </p>
              <ul className="service-features">
                <li>Alternative temporary living expenses</li>
                <li>Fine art and high-value item riders</li>
                <li>Natural disaster resilience options</li>
              </ul>
            </div>

            <div className="service-card">
              <div className="service-icon">🏢</div>
              <h3>Commercial Risk & Fleet</h3>
              <p>
                Enterprise property insurance, professional indemnity, key-man coverage, and business interruption safeguards.
              </p>
              <ul className="service-features">
                <li>Supply chain disruption recovery</li>
                <li>Comprehensive cyber risk endorsements</li>
                <li>Tailored group benefits programs</li>
              </ul>
            </div>

            <div className="service-card">
              <div className="service-icon">📜</div>
              <h3>Term Life & Endowment</h3>
              <p>
                Financial preservation and legacy planning ensuring family stability and mortgage payoff in unforeseen circumstances.
              </p>
              <ul className="service-features">
                <li>Guaranteed death benefit payout</li>
                <li>Flexible premium term structures</li>
                <li>Tax-advantaged savings components</li>
              </ul>
            </div>

            <div className="service-card">
              <div className="service-icon">⚡</div>
              <h3>Rapid Claims Service</h3>
              <p>
                Seamless digital reporting for policyholders and expedited review workflows for underwriters with paperless resolution.
              </p>
              <ul className="service-features">
                <li>Direct ACH settlement disbursement</li>
                <li>Live status tracking at every milestone</li>
                <li>Dedicated claims specialist support</li>
              </ul>
            </div>
          </div>
        </div>
      </section>

      {/* CONTACT SECTION */}
      <section id="contact" className="section-wrapper">
        <div className="section-header">
          <div className="section-tag">Get in Touch</div>
          <h2 className="section-title">24/7 Advisory & Claims Assistance</h2>
          <p className="section-subtitle">
            Our claims adjusters and licensed insurance advisors are standing by.
          </p>
        </div>

        <div className="contact-container">
          <div className="contact-info">
            <div className="contact-info-item">
              <div className="contact-info-icon">📍</div>
              <div className="contact-info-text">
                <h4>Global Headquarters</h4>
                <p>Aegis Financial Tower, 450 North Michigan Avenue, Chicago, IL 60611</p>
              </div>
            </div>

            <div className="contact-info-item">
              <div className="contact-info-icon">📞</div>
              <div className="contact-info-text">
                <h4>24/7 Emergency Claims Hotline</h4>
                <p>1-800-555-AEGIS (2344) / +1 (312) 555-0199</p>
              </div>
            </div>

            <div className="contact-info-item">
              <div className="contact-info-icon">✉️</div>
              <div className="contact-info-text">
                <h4>Underwriting & Customer Support</h4>
                <p>adjudication@aegis-assurance.com / claims@aegis-assurance.com</p>
              </div>
            </div>
          </div>

          <div style={{ background: '#f8fafc', padding: '32px', borderRadius: '12px', border: '1px solid #e2e8f0' }}>
            <h4 style={{ color: '#0b2545', fontSize: '1.15rem', fontWeight: '700', marginBottom: '12px' }}>
              Member & Staff Access
            </h4>
            <p style={{ color: '#475569', fontSize: '0.95rem', marginBottom: '24px', lineHeight: '1.6' }}>
              Are you an active policyholder or an authorized Aegis claims officer? Access your dedicated dashboard to manage policies or review pending claims.
            </p>
            <button
              onClick={() => navigate('/login')}
              className="btn btn-primary"
              style={{ width: '100%' }}
            >
              Go to Portal Login →
            </button>
          </div>
        </div>
      </section>

      {/* FOOTER */}
      <footer className="home-footer">
        <div className="footer-inner">
          <div className="brand-logo" style={{ color: '#ffffff' }}>
            AEGIS ASSURANCE GROUP
          </div>
          <div style={{ fontSize: '0.9rem', color: '#94a3b8' }}>
            Licensed & Regulated Institutional Insurance Carrier
          </div>
        </div>
        <div className="footer-copy">
          © {new Date().getFullYear()} Aegis Assurance Group Corporation. All rights reserved. Confidential Policyholder & Staff Gateway.
        </div>
      </footer>
    </div>
  );
}
