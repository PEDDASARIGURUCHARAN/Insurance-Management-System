import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Footer from '../components/Footer';
import heroImg from '../assets/hero.png';

/* ─────────────────────────────────────────────────────────────────────────────
   INLINE SVG ICON COMPONENTS
   ───────────────────────────────────────────────────────────────────────────── */

const ShieldIcon = () => (
  <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
    <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z" />
  </svg>
);

const HeartPulseIcon = () => (
  <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
    <path d="M19 14c1.49-1.46 3-3.21 3-5.5A5.5 5.5 0 0 0 16.5 3c-1.76 0-3 .5-4.5 2-1.5-1.5-2.74-2-4.5-2A5.5 5.5 0 0 0 2 8.5c0 2.3 1.5 4.05 3 5.5l7 7Z" />
    <path d="M3.22 12H9.5l1.5-3 2 4.5 1.5-3.5h6.28" />
  </svg>
);

const CarIcon = () => (
  <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
    <path d="M19 17H5v2H3v-5l2-5h14l2 5v5h-2v-2Z" />
    <circle cx="7.5" cy="17.5" r="1.5" />
    <circle cx="16.5" cy="17.5" r="1.5" />
    <path d="M5 12h14" />
  </svg>
);

const HouseIcon = () => (
  <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
    <path d="M3 9.5 12 3l9 6.5V20a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1Z" />
    <path d="M9 21V12h6v9" />
  </svg>
);

const PlaneIcon = () => (
  <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
    <path d="M17.8 19.2 16 11l3.5-3.5C21 6 21 4 21 4s-2 0-3.5 1.5L14 9 5.8 6.2a2 2 0 0 0-2 .5L2 8l9 4-2 3-3-1-1 2 4 2 2 4 2-1-1-3 3-2 4 9 1.8-1.8a2 2 0 0 0 .5-2Z" />
  </svg>
);

const UserRoundIcon = () => (
  <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
    <circle cx="12" cy="8" r="4" />
    <path d="M4 20c0-4 3.6-7 8-7s8 3 8 7" />
    <path d="M17 11l1.5 1.5L21 10" />
  </svg>
);

const BriefcaseIcon = () => (
  <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
    <rect x="2" y="7" width="20" height="14" rx="2" />
    <path d="M16 7V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v2" />
    <line x1="12" y1="12" x2="12" y2="17" />
    <line x1="8" y1="14.5" x2="16" y2="14.5" />
  </svg>
);

const FileTextIcon = () => (
  <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
    <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8Z" />
    <polyline points="14 2 14 8 20 8" />
    <line x1="8" y1="13" x2="16" y2="13" />
    <line x1="8" y1="17" x2="16" y2="17" />
    <line x1="8" y1="9" x2="10" y2="9" />
  </svg>
);

const PencilIcon = () => (
  <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
    <path d="M17 3a2.85 2.85 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5Z" />
    <path d="m15 5 4 4" />
  </svg>
);

const CircleCheckIcon = () => (
  <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
    <circle cx="12" cy="12" r="10" />
    <path d="m9 12 2 2 4-4" />
  </svg>
);

const ShieldCheckIcon = () => (
  <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
    <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z" />
    <path d="m9 12 2 2 4-4" />
  </svg>
);

const ArrowRightIcon = () => (
  <svg viewBox="0 0 24 24" fill="none" strokeWidth="2.2" strokeLinecap="round" strokeLinejoin="round" width="16" height="16">
    <path d="M5 12h14M12 5l7 7-7 7" />
  </svg>
);

const UsersIcon = () => (
  <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
    <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2" />
    <circle cx="9" cy="7" r="4" />
    <path d="M23 21v-2a4 4 0 0 0-3-3.87M16 3.13a4 4 0 0 1 0 7.75" />
  </svg>
);

const StarIcon = () => (
  <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
    <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2" />
  </svg>
);

const ClipboardListIcon = () => (
  <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
    <rect x="8" y="2" width="8" height="4" rx="1" />
    <path d="M16 4h2a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h2" />
    <path d="M12 11h4M12 16h4M8 11h.01M8 16h.01" />
  </svg>
);

const CpuIcon = () => (
  <svg viewBox="0 0 24 24" fill="none" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
    <rect x="4" y="4" width="16" height="16" rx="2" />
    <rect x="9" y="9" width="6" height="6" />
    <path d="M15 2v2M15 20v2M9 2v2M9 20v2M2 15h2M20 15h2M2 9h2M20 9h2" />
  </svg>
);

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

const CheckIcon = () => (
  <svg viewBox="0 0 24 24" fill="none" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round">
    <polyline points="20 6 9 17 4 12" />
  </svg>
);

/* ─────────────────────────────────────────────────────────────────────────────
   POLICY CARDS DATA
   ───────────────────────────────────────────────────────────────────────────── */

const POLICIES = [
  {
    id: 'health',
    icon: <HeartPulseIcon />,
    title: 'Health Insurance',
    desc: 'Quality healthcare coverage for a healthier tomorrow.',
  },
  {
    id: 'motor',
    icon: <CarIcon />,
    title: 'Motor Insurance',
    desc: 'Protection for your vehicle and a safer journey ahead.',
  },
  {
    id: 'property',
    icon: <HouseIcon />,
    title: 'Property Insurance',
    desc: 'Secure your home and assets against life\'s uncertainties.',
  },
  {
    id: 'travel',
    icon: <PlaneIcon />,
    title: 'Travel Insurance',
    desc: 'Travel with confidence across the world.',
  },
  {
    id: 'accident',
    icon: <UserRoundIcon />,
    title: 'Personal Accident',
    desc: 'Financial support during unexpected accidents.',
  },
  {
    id: 'business',
    icon: <BriefcaseIcon />,
    title: 'Business Insurance',
    desc: 'Tailored coverage for your business growth and continuity.',
  },
];

/* ─────────────────────────────────────────────────────────────────────────────
   HOW IT WORKS DATA
   ───────────────────────────────────────────────────────────────────────────── */

const HOW_STEPS = [
  {
    num: 1,
    icon: <FileTextIcon />,
    title: 'Choose a Policy',
    desc: 'Select the insurance plan that fits your needs.',
  },
  {
    num: 2,
    icon: <PencilIcon />,
    title: 'Complete Application',
    desc: 'Provide the required details securely.',
  },
  {
    num: 3,
    icon: <CircleCheckIcon />,
    title: 'Get Confirmation',
    desc: 'Receive instant policy confirmation.',
  },
  {
    num: 4,
    icon: <ShieldCheckIcon />,
    title: 'Stay Protected',
    desc: 'Enjoy peace of mind, always.',
  },
];

/* ─────────────────────────────────────────────────────────────────────────────
   HOME PAGE COMPONENT
   ───────────────────────────────────────────────────────────────────────────── */

export default function HomePage() {
  const navigate = useNavigate();
  const [scrolled, setScrolled] = useState(false);

  useEffect(() => {
    const handleScroll = () => setScrolled(window.scrollY > 10);
    window.addEventListener('scroll', handleScroll, { passive: true });
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  const scrollToSection = (id) => {
    const el = document.getElementById(id);
    if (el) {
      el.scrollIntoView({ behavior: 'smooth', block: 'start' });
    } else {
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }
  };

  return (
    <div className="home-container">

      {/* ═══════════════════════════════════════════════════
          NAVBAR
      ═══════════════════════════════════════════════════ */}
      <nav
        className="home-navbar"
        style={scrolled ? { boxShadow: '0 2px 16px rgba(7,30,61,0.10)' } : {}}
        role="navigation"
        aria-label="Main navigation"
      >
        {/* Brand / Logo */}
        <div className="brand-logo" aria-label="Aegis Assurance Group">
          <img
            src="/agile-insurance-logo.png"
            alt="Aegis Assurance Group Logo"
            className="brand-logo-img"
            onError={(e) => { e.target.style.display = 'none'; }}
          />
          <div className="brand-logo-text">
            <span className="brand-logo-name">
              AEGIS <span>ASSURANCE</span> GROUP
            </span>
            <span className="brand-logo-sub">Protecting What Matters</span>
          </div>
        </div>

        {/* Nav Links */}
        <ul className="nav-links" role="list">
          <li>
            <button
              className="nav-link"
              onClick={() => window.scrollTo({ top: 0, behavior: 'smooth' })}
            >
              Home
            </button>
          </li>
          <li>
            <button
              className="nav-link"
              onClick={() => scrollToSection('about')}
            >
              About
            </button>
          </li>
          <li>
            <button
              className="nav-link"
              onClick={() => scrollToSection('policies')}
            >
              Policies
            </button>
          </li>
          <li>
            <button
              className="nav-link"
              onClick={() => scrollToSection('claims')}
            >
              Claims
            </button>
          </li>
          <li>
            <button
              className="nav-link"
              onClick={() => scrollToSection('contact')}
            >
              Contact
            </button>
          </li>
          <li>
            <button
              id="nav-login-btn"
              className="nav-login-btn"
              onClick={() => navigate('/login')}
              aria-label="Login to portal"
            >
              Login
            </button>
          </li>
        </ul>
      </nav>


      {/* ═══════════════════════════════════════════════════
          HERO SECTION
      ═══════════════════════════════════════════════════ */}
      <header className="hero-section" aria-label="Hero">
        <div className="hero-inner">
          {/* Left Content */}
          <div className="hero-content">
            <div className="hero-eyebrow">
              TRUST
              <span className="hero-eyebrow-dot">•</span>
              SECURITY
              <span className="hero-eyebrow-dot">•</span>
              A BRIGHTER TOMORROW
            </div>

            <h1 className="hero-title">
              Insurance for a<br />
              <span className="hero-title-blue">Safer Tomorrow</span>
            </h1>

            <p className="hero-desc">
              At Aegis Assurance Group, we provide reliable insurance
              solutions to protect you, your family, and your future.
              Simpler policies. Faster claims. Greater peace of mind.
            </p>

            <div className="hero-actions">
              <button
                id="hero-explore-policies-btn"
                className="hero-btn-primary"
                onClick={() => scrollToSection('policies')}
              >
                Explore Our Policies
                <ArrowRightIcon />
              </button>
              <button
                id="hero-file-claim-btn"
                className="hero-btn-secondary"
                onClick={() => navigate('/login')}
              >
                File a Claim
              </button>
            </div>
          </div>

          {/* Right — Hero Image */}
          <div className="hero-image-side" aria-hidden="true">
            <div className="hero-img-wrap">
              <img
                src={heroImg}
                alt="Family protected by Aegis insurance"
                onError={(e) => {
                  e.target.parentElement.style.background =
                    'linear-gradient(135deg, #0B2D52 0%, #1565C0 100%)';
                  e.target.style.display = 'none';
                }}
              />
              <div className="hero-img-fade" />
            </div>

            {/* Overlay tagline card */}
            <div className="hero-overlay-card">
              <span className="hero-overlay-card-line" />
              <p>
                Together<br />
                Towards a<br />
                Safer Future
              </p>
            </div>
          </div>
        </div>
      </header>


      {/* ═══════════════════════════════════════════════════
          INSURANCE POLICIES SECTION
      ═══════════════════════════════════════════════════ */}
      <section id="policies" className="home-section policies-section" aria-label="Our insurance policies">
        <div className="home-section-inner">
          <div className="section-header-center">
            <div className="section-label">Our Insurance Policies</div>
            <h2 className="section-heading">
              OUR INSURANCE <span className="section-heading-blue">POLICIES</span>
            </h2>
            <p className="section-subtitle">
              Comprehensive insurance policies for individuals, families, and businesses.
            </p>
          </div>

          <div className="policy-cards-grid">
            {POLICIES.map((policy) => (
              <div key={policy.id} className="policy-card" role="article">
                <div className="policy-icon-wrap" aria-hidden="true">
                  {policy.icon}
                </div>
                <h3 className="policy-card-title">{policy.title}</h3>
                <p className="policy-card-desc">{policy.desc}</p>
                <button
                  className="policy-card-link"
                  onClick={() => scrollToSection('policies')}
                  aria-label={`Learn more about ${policy.title}`}
                >
                  Learn More
                  <ArrowRightIcon />
                </button>
              </div>
            ))}
          </div>
        </div>
      </section>


      {/* ═══════════════════════════════════════════════════
          ABOUT AEGIS SECTION
      ═══════════════════════════════════════════════════ */}
      <section id="about" className="home-section about-section" aria-label="About Aegis">
        <div className="home-section-inner">
          <div className="about-grid">

            {/* Left — Text */}
            <div className="about-text-col">
              <div className="section-label">About Aegis Assurance Group</div>
              <h2 className="section-heading">
                Your Trusted Partner<br />
                <span className="section-heading-blue">in Insurance</span>
              </h2>
              <p className="about-desc">
                Aegis Assurance Group is committed to delivering reliable,
                transparent, and customer-first insurance solutions. With
                innovation and integrity at our core, we help individuals,
                families, and businesses stay protected through every stage
                of life.
              </p>
              <button
                id="about-more-btn"
                className="about-btn"
                onClick={() => scrollToSection('about')}
                aria-label="More about us"
              >
                More About Us
                <ArrowRightIcon />
              </button>
            </div>

            {/* Middle — Stats */}
            <div className="about-stats-col">
              <div className="about-stat-card">
                <div className="about-stat-icon" aria-hidden="true">
                  <UsersIcon />
                </div>
                <div>
                  <div className="about-stat-num">1M+</div>
                  <div className="about-stat-label">Happy Customers</div>
                </div>
              </div>

              <div className="about-stat-card">
                <div className="about-stat-icon" aria-hidden="true">
                  <CircleCheckIcon />
                </div>
                <div>
                  <div className="about-stat-num">99.4%</div>
                  <div className="about-stat-label">Claim Settlement Rate</div>
                </div>
              </div>

              <div className="about-stat-card">
                <div className="about-stat-icon" aria-hidden="true">
                  <StarIcon />
                </div>
                <div>
                  <div className="about-stat-num">A+</div>
                  <div className="about-stat-label">Trusted by Experts</div>
                </div>
              </div>
            </div>

            {/* Right — Image + Overlay Card */}
            <div className="about-image-col" aria-hidden="true">
              <div className="about-img-wrap">
                <img
                  src="/agile-insurance-logo.png"
                  alt="Aegis office"
                  className="about-img-placeholder"
                  style={{ objectFit: 'contain', padding: '32px', background: 'linear-gradient(135deg, #071E3D 0%, #0B2D52 100%)' }}
                  onError={(e) => { e.target.style.display = 'none'; }}
                />
              </div>
              <div className="about-overlay-card">
                <span className="about-overlay-card-teal-line" />
                <p>Protecting people.<br />Empowering possibilities.</p>
              </div>
            </div>

          </div>
        </div>
      </section>


      {/* ═══════════════════════════════════════════════════
          DIGITAL INSURANCE STRIP
      ═══════════════════════════════════════════════════ */}
      <div className="digital-strip" aria-label="Digital insurance platform">
        <div className="digital-strip-inner">
          <div className="digital-strip-left">
            <div className="digital-strip-icon" aria-hidden="true">
              <CpuIcon />
            </div>
            <div>
              <div className="digital-strip-title">Digital Insurance Experience</div>
              <div className="digital-strip-bullets">
                Secure Policies&nbsp;&nbsp;•&nbsp;&nbsp;Intelligent Claims&nbsp;&nbsp;•&nbsp;&nbsp;Faster Decisions
              </div>
            </div>
          </div>
          <div className="digital-strip-right">
            Manage policies, submit claims and track decisions through a
            secure digital insurance platform built on modern microservices architecture.
          </div>
        </div>
      </div>


      {/* ═══════════════════════════════════════════════════
          HOW IT WORKS
      ═══════════════════════════════════════════════════ */}
      <section id="how" className="home-section how-section" aria-label="How it works">
        <div className="home-section-inner">
          <div className="section-header-center">
            <div className="section-label">How It Works</div>
            <h2 className="section-heading">
              Simple Steps,{' '}
              <span className="section-heading-blue">Complete Protection</span>
            </h2>
            <p className="section-subtitle">
              Getting insured with Aegis is quick and easy.
            </p>
          </div>

          <div className="how-steps-row">
            {HOW_STEPS.map((step) => (
              <div key={step.num} className="how-step">
                <div className="how-step-num-wrap" aria-hidden="true">
                  {step.icon}
                  <span className="how-step-badge">{step.num}</span>
                </div>
                <h3 className="how-step-title">{step.title}</h3>
                <p className="how-step-desc">{step.desc}</p>
              </div>
            ))}
          </div>
        </div>
      </section>


      {/* ═══════════════════════════════════════════════════
          CLAIMS / DIGITAL PROCESS HIGHLIGHT
      ═══════════════════════════════════════════════════ */}
      <section id="claims" className="home-section claims-section" aria-label="Claims process">
        <div className="home-section-inner">
          <div className="claims-inner">

            {/* Left */}
            <div>
              <div className="section-label">Claims</div>
              <h2 className="claims-heading">
                Faster Claims.<br />
                <span>Clearer Decisions.</span>
              </h2>
              <p className="claims-desc">
                Submit claims digitally and track their progress through
                a transparent, secure adjudication workflow.
              </p>
              <div className="claims-points">
                <div className="claims-point">
                  <div className="claims-check" aria-hidden="true">
                    <CheckIcon />
                  </div>
                  Digital Claim Submission
                </div>
                <div className="claims-point">
                  <div className="claims-check" aria-hidden="true">
                    <CheckIcon />
                  </div>
                  Real-Time Claim Status
                </div>
                <div className="claims-point">
                  <div className="claims-check" aria-hidden="true">
                    <CheckIcon />
                  </div>
                  Automated Policy Validation
                </div>
              </div>
            </div>

            {/* Right — process card */}
            <div className="claims-card">
              <div className="claims-card-header">
                <div className="claims-card-icon" aria-hidden="true">
                  <ClipboardListIcon />
                </div>
                <div>
                  <div className="claims-card-title">Aegis Claims Portal</div>
                  <div className="claims-card-sub">Digital adjudication workflow</div>
                </div>
              </div>

              <div className="claims-card-steps">
                <div className="claims-card-step">
                  <div className="claims-card-step-dot" />
                  <span className="claims-card-step-text">Submit claim with supporting documents</span>
                </div>
                <div className="claims-card-step">
                  <div className="claims-card-step-dot" />
                  <span className="claims-card-step-text">Automated policy validation in real-time</span>
                </div>
                <div className="claims-card-step">
                  <div className="claims-card-step-dot" />
                  <span className="claims-card-step-text">Underwriter review and decision</span>
                </div>
              </div>
            </div>

          </div>
        </div>
      </section>


      {/* ═══════════════════════════════════════════════════
          CALL TO ACTION
      ═══════════════════════════════════════════════════ */}
      <div id="contact" className="cta-section" aria-label="Call to action">
        <div className="cta-banner">
          <div className="cta-left">
            <h2 className="cta-heading">
              Ready to Secure Your Tomorrow?
            </h2>
            <p className="cta-sub">
              Explore our insurance plans or file a claim today.
            </p>
          </div>
          <div className="cta-actions">
            <button
              id="cta-view-policies-btn"
              className="cta-btn-primary"
              onClick={() => scrollToSection('policies')}
            >
              View Policies
              <ArrowRightIcon />
            </button>
            <button
              id="cta-file-claim-btn"
              className="cta-btn-secondary"
              onClick={() => navigate('/login')}
            >
              File a Claim
            </button>
          </div>
        </div>
      </div>


      {/* ═══════════════════════════════════════════════════
          FOOTER
      ═══════════════════════════════════════════════════ */}
      <Footer />

    </div>
  );
}
