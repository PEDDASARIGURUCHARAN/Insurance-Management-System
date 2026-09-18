import React from 'react';
import { Link } from 'react-router-dom';

export default function Footer() {
  const scrollToSection = (id) => {
    const element = document.getElementById(id);
    if (element) {
      element.scrollIntoView({ behavior: 'smooth' });
    } else {
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }
  };

  const handleNavClick = (e, sectionId) => {
    if (window.location.pathname === '/') {
      e.preventDefault();
      scrollToSection(sectionId);
    }
  };

  return (
    <footer className="aegis-footer" role="contentinfo">
      <div className="footer-container">
        {/* TOP / MAIN FOOTER GRID */}
        <div className="footer-grid">
          {/* 1. BRAND COLUMN */}
          <div className="footer-col brand-col">
            <h3 className="footer-brand-title">Aegis Insurance Group</h3>
            <p className="footer-brand-desc">
              Your trusted partner for a safer tomorrow. Providing reliable insurance solutions for individuals, families and businesses.
            </p>
          </div>

          {/* 2. QUICK LINKS */}
          <div className="footer-col">
            <h4 className="footer-heading">Quick Links</h4>
            <nav aria-label="Quick Links">
              <ul className="footer-links">
                <li>
                  <Link to="/" onClick={(e) => handleNavClick(e, 'top')}>
                    Home
                  </Link>
                </li>
                <li>
                  <a href="/#about" onClick={(e) => handleNavClick(e, 'about')}>
                    About Us
                  </a>
                </li>
                <li>
                  <a href="/#services" onClick={(e) => handleNavClick(e, 'services')}>
                    Our Products
                  </a>
                </li>
                <li>
                  <Link to="/login">Claims</Link>
                </li>
                <li>
                  <a href="/#contact" onClick={(e) => handleNavClick(e, 'contact')}>
                    Contact Us
                  </a>
                </li>
              </ul>
            </nav>
          </div>

          {/* 3. OUR INSURANCE PRODUCTS */}
          <div className="footer-col">
            <h4 className="footer-heading">Our Insurance Products</h4>
            <nav aria-label="Our Insurance Products">
              <ul className="footer-links">
                <li>
                  <a href="/#services" onClick={(e) => handleNavClick(e, 'services')}>
                    Health Insurance
                  </a>
                </li>
                <li>
                  <a href="/#services" onClick={(e) => handleNavClick(e, 'services')}>
                    Life Insurance
                  </a>
                </li>
                <li>
                  <a href="/#services" onClick={(e) => handleNavClick(e, 'services')}>
                    Motor Insurance
                  </a>
                </li>
                <li>
                  <a href="/#services" onClick={(e) => handleNavClick(e, 'services')}>
                    Travel Insurance
                  </a>
                </li>
                <li>
                  <a href="/#services" onClick={(e) => handleNavClick(e, 'services')}>
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
                  <Link to="/login">File a Claim</Link>
                </li>
                <li>
                  <Link to="/login">Track Your Claim</Link>
                </li>
                <li>
                  <a href="/#services" onClick={(e) => handleNavClick(e, 'services')}>
                    FAQs
                  </a>
                </li>
                <li>
                  <Link to="/login">Policy Documents</Link>
                </li>
                <li>
                  <a href="/#contact" onClick={(e) => handleNavClick(e, 'contact')}>
                    Grievance Redressal
                  </a>
                </li>
              </ul>
            </nav>
          </div>

          {/* 5. CONTACT US */}
          <div className="footer-col contact-col">
            <h4 className="footer-heading">Contact Us</h4>
            <div className="footer-contact-list">
              <div className="footer-contact-item">
                <span className="contact-icon" aria-hidden="true">
                  <svg
                    width="16"
                    height="16"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                  >
                    <rect x="2" y="4" width="20" height="16" rx="2" />
                    <path d="m22 7-8.97 5.7a1.94 1.94 0 0 1-2.06 0L2 7" />
                  </svg>
                </span>
                <a href="mailto:support@aegisinsurance.com" className="contact-link">
                  support@aegisinsurance.com
                </a>
              </div>

              <div className="footer-contact-item">
                <span className="contact-icon" aria-hidden="true">
                  <svg
                    width="16"
                    height="16"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                  >
                    <circle cx="12" cy="12" r="10" />
                    <polyline points="12 6 12 12 16 14" />
                  </svg>
                </span>
                <span className="contact-text">24/7 Service</span>
              </div>
            </div>
          </div>
        </div>

        {/* THIN HORIZONTAL DIVIDER */}
        <div className="footer-divider" />

        {/* BOTTOM BAR */}
        <div className="footer-bottom-bar">
          <div className="footer-copyright">
            © 2026 Aegis Insurance Group. All rights reserved.
          </div>
          <div className="footer-legal-links">
            <a href="#privacy" className="legal-link">Privacy Policy</a>
            <span className="legal-separator">|</span>
            <a href="#terms" className="legal-link">Terms &amp; Conditions</a>
            <span className="legal-separator">|</span>
            <a href="#sitemap" className="legal-link">Sitemap</a>
          </div>
        </div>
      </div>
    </footer>
  );
}
