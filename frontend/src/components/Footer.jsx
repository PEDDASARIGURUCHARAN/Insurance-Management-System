import React from 'react';
import { Link, useNavigate } from 'react-router-dom';

/* ─── Inline SVG icons ───────────────────────────────────────────────────── */

const MailIcon = () => (
  <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
    <rect x="2" y="4" width="20" height="16" rx="2" />
    <path d="m22 7-8.97 5.7a1.94 1.94 0 0 1-2.06 0L2 7" />
  </svg>
);

const ClockIcon = () => (
  <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
    <circle cx="12" cy="12" r="10" />
    <polyline points="12 6 12 12 16 14" />
  </svg>
);

/* ─────────────────────────────────────────────────────────────────────────────
   FOOTER COMPONENT
   ───────────────────────────────────────────────────────────────────────────── */

export default function Footer() {
  const navigate = useNavigate();

  const scrollToSection = (id) => {
    if (window.location.pathname === '/') {
      const el = document.getElementById(id);
      if (el) {
        el.scrollIntoView({ behavior: 'smooth', block: 'start' });
        return;
      }
    }
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  const handleSectionClick = (e, sectionId) => {
    if (window.location.pathname === '/') {
      e.preventDefault();
      scrollToSection(sectionId);
    }
  };

  return (
    <footer className="aegis-footer" role="contentinfo">
      <div className="footer-container">

        {/* ─── MAIN GRID ────────────────────────────────────────── */}
        <div className="footer-grid">

          {/* 1. BRAND COLUMN */}
          <div className="footer-col brand-col">
            <div className="footer-brand-title">
              AEGIS <span>ASSURANCE</span> GROUP
            </div>
            <div className="footer-brand-tagline">Protecting What Matters</div>
            <p className="footer-brand-desc">
              Your trusted partner for a safer tomorrow.
              Providing reliable insurance solutions
              for individuals, families and businesses.
            </p>
          </div>

          {/* 2. QUICK LINKS */}
          <div className="footer-col">
            <h4 className="footer-heading">Quick Links</h4>
            <nav aria-label="Quick Links">
              <ul className="footer-links">
                <li>
                  <Link to="/" onClick={(e) => handleSectionClick(e, 'top')}>
                    Home
                  </Link>
                </li>
                <li>
                  <a href="/#about" onClick={(e) => handleSectionClick(e, 'about')}>
                    About Us
                  </a>
                </li>
                <li>
                  <a href="/#policies" onClick={(e) => handleSectionClick(e, 'policies')}>
                    Our Policies
                  </a>
                </li>
                <li>
                  <a href="/#claims" onClick={(e) => handleSectionClick(e, 'claims')}>
                    Claims
                  </a>
                </li>
                <li>
                  <a href="/#contact" onClick={(e) => handleSectionClick(e, 'contact')}>
                    Contact Us
                  </a>
                </li>
              </ul>
            </nav>
          </div>

          {/* 3. OUR POLICIES */}
          <div className="footer-col">
            <h4 className="footer-heading">Our Policies</h4>
            <nav aria-label="Our Policies">
              <ul className="footer-links">
                <li>
                  <a href="/#policies" onClick={(e) => handleSectionClick(e, 'policies')}>
                    Health Insurance
                  </a>
                </li>
                <li>
                  <a href="/#policies" onClick={(e) => handleSectionClick(e, 'policies')}>
                    Motor Insurance
                  </a>
                </li>
                <li>
                  <a href="/#policies" onClick={(e) => handleSectionClick(e, 'policies')}>
                    Property Insurance
                  </a>
                </li>
                <li>
                  <a href="/#policies" onClick={(e) => handleSectionClick(e, 'policies')}>
                    Travel Insurance
                  </a>
                </li>
                <li>
                  <a href="/#policies" onClick={(e) => handleSectionClick(e, 'policies')}>
                    Personal Accident
                  </a>
                </li>
                <li>
                  <a href="/#policies" onClick={(e) => handleSectionClick(e, 'policies')}>
                    Business Insurance
                  </a>
                </li>
              </ul>
            </nav>
          </div>

          {/* 4. CUSTOMER SUPPORT */}
          <div className="footer-col">
            <h4 className="footer-heading">Customer Support</h4>
            <nav aria-label="Customer Support">
              <ul className="footer-links">
                <li>
                  <button onClick={() => navigate('/login')}>
                    File a Claim
                  </button>
                </li>
                <li>
                  <button onClick={() => navigate('/login')}>
                    Track Your Claim
                  </button>
                </li>
                <li>
                  <a href="/#policies" onClick={(e) => handleSectionClick(e, 'policies')}>
                    FAQs
                  </a>
                </li>
                <li>
                  <button onClick={() => navigate('/login')}>
                    Policy Documents
                  </button>
                </li>
                <li>
                  <a href="/#contact" onClick={(e) => handleSectionClick(e, 'contact')}>
                    Grievance Redressal
                  </a>
                </li>
              </ul>
            </nav>
          </div>

          {/* 5. STAY CONNECTED */}
          <div className="footer-col contact-col">
            <h4 className="footer-heading">Stay Connected</h4>
            <div className="footer-contact-list">
              <div className="footer-contact-item">
                <MailIcon />
                <a
                  href="mailto:support@aegisinsurance.com"
                  className="contact-link"
                >
                  support@aegisinsurance.com
                </a>
              </div>
              <div className="footer-contact-item">
                <ClockIcon />
                <span className="contact-text">24/7 Customer Support</span>
              </div>
            </div>
          </div>

        </div>

        {/* ─── DIVIDER ──────────────────────────────────────────── */}
        <div className="footer-divider" />

        {/* ─── BOTTOM BAR ───────────────────────────────────────── */}
        <div className="footer-bottom-bar">
          <div className="footer-copyright">
            © 2026 Aegis Assurance Group. All rights reserved.
          </div>
        </div>

      </div>
    </footer>
  );
}
