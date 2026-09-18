import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { MOCK_EMPLOYEES } from '../mockData';

export default function AdminDashboard({ onLogout }) {
  const navigate = useNavigate();
  const employee = MOCK_EMPLOYEES['2400030001'];

  const [activeTab, setActiveTab] = useState('Dashboard');
  const [pendingClaims, setPendingClaims] = useState(employee.pendingClaims);
  const [allClaims, setAllClaims] = useState(employee.allClaims);
  const [pendingReports, setPendingReports] = useState(employee.pendingReports);
  const [selectedClaimForReview, setSelectedClaimForReview] = useState(null);
  const [reviewNote, setReviewNote] = useState('');
  const [reviewSuccessMessage, setReviewSuccessMessage] = useState('');

  // Counters
  const [stats, setStats] = useState({
    totalClaims: employee.stats.totalClaims,
    pendingClaims: employee.stats.pendingClaims,
    approvedClaims: employee.stats.approvedClaims,
    rejectedClaims: employee.stats.rejectedClaims,
    pendingReports: employee.stats.pendingReports
  });

  const handleLogout = () => {
    if (onLogout) onLogout();
    navigate('/');
  };

  const openReviewModal = (claim) => {
    setSelectedClaimForReview(claim);
    setReviewNote('');
    setReviewSuccessMessage('');
  };

  const closeReviewModal = () => {
    setSelectedClaimForReview(null);
  };

  const handleDecision = (decision) => {
    if (!selectedClaimForReview) return;

    const claimId = selectedClaimForReview.id;
    // Remove from pending list
    setPendingClaims(pendingClaims.filter(c => c.id !== claimId));

    // Update in all claims
    setAllClaims(allClaims.map(c => {
      if (c.id === claimId) {
        return { ...c, status: decision };
      }
      return c;
    }));

    // Update stats
    setStats(prev => ({
      ...prev,
      pendingClaims: Math.max(0, prev.pendingClaims - 1),
      approvedClaims: decision === 'Approved' ? prev.approvedClaims + 1 : prev.approvedClaims,
      rejectedClaims: decision === 'Rejected' ? prev.rejectedClaims + 1 : prev.rejectedClaims
    }));

    setReviewSuccessMessage(`Claim ${claimId} has been successfully ${decision.toUpperCase()}!`);
    setTimeout(() => {
      closeReviewModal();
    }, 1200);
  };

  return (
    <div className="dashboard-layout">
      {/* SIDEBAR */}
      <aside className="dashboard-sidebar">
        <div className="sidebar-header">
          <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="#60a5fa" strokeWidth="2">
            <path d="M12 2L2 7l10 5 10-5-10-5zM2 17l10 5 10-5M2 12l10 5 10-5" />
          </svg>
          <div>
            <div className="brand-title">AEGIS ASSURANCE</div>
            <div className="portal-badge">Employee / Admin Portal</div>
          </div>
        </div>

        <nav className="sidebar-nav">
          <button
            className={`sidebar-nav-item ${activeTab === 'Dashboard' ? 'active' : ''}`}
            onClick={() => setActiveTab('Dashboard')}
          >
            <span>📊</span> Dashboard
          </button>
          <button
            className={`sidebar-nav-item ${activeTab === 'Pending Claims' ? 'active' : ''}`}
            onClick={() => setActiveTab('Pending Claims')}
          >
            <span>⏳</span> Pending Claims ({pendingClaims.length})
          </button>
          <button
            className={`sidebar-nav-item ${activeTab === 'All Claims' ? 'active' : ''}`}
            onClick={() => setActiveTab('All Claims')}
          >
            <span>📑</span> All Claims
          </button>
          <button
            className={`sidebar-nav-item ${activeTab === 'Policy Verification' ? 'active' : ''}`}
            onClick={() => setActiveTab('Policy Verification')}
          >
            <span>🛡️</span> Policy Verification
          </button>
          <button
            className={`sidebar-nav-item ${activeTab === 'Claim Review' ? 'active' : ''}`}
            onClick={() => setActiveTab('Claim Review')}
          >
            <span>🔍</span> Claim Review
          </button>
          <button
            className={`sidebar-nav-item ${activeTab === 'Reports' ? 'active' : ''}`}
            onClick={() => setActiveTab('Reports')}
          >
            <span>📈</span> Reports ({stats.pendingReports})
          </button>
          <button
            className={`sidebar-nav-item ${activeTab === 'Profile' ? 'active' : ''}`}
            onClick={() => setActiveTab('Profile')}
          >
            <span>👤</span> Profile
          </button>
        </nav>

        <div className="sidebar-footer">
          <button
            id="admin-logout-btn"
            className="sidebar-nav-item logout"
            onClick={handleLogout}
          >
            <span>🚪</span> Logout
          </button>
        </div>
      </aside>

      {/* MAIN VIEW */}
      <main className="dashboard-main">
        {/* TOPBAR */}
        <header className="dashboard-topbar">
          <div className="topbar-left">
            <h2>{activeTab}</h2>
          </div>
          <div className="topbar-right">
            <div className="user-profile-badge">
              <div className="avatar" style={{ backgroundColor: '#134074', color: '#ffffff' }}>MV</div>
              <div className="user-meta">
                <div className="user-name">{employee.name}</div>
                <div className="user-role">{employee.role} | ID: {employee.id}</div>
              </div>
            </div>
            <button onClick={handleLogout} className="btn btn-secondary btn-sm">
              Logout
            </button>
          </div>
        </header>

        {/* CONTENT AREA */}
        <div className="dashboard-content">
          {/* 1. DASHBOARD VIEW */}
          {activeTab === 'Dashboard' && (
            <div>
              {/* Officer Header Banner */}
              <div style={{
                background: 'linear-gradient(135deg, #06182c 0%, #0b2545 100%)',
                color: '#ffffff',
                padding: '24px 28px',
                borderRadius: '14px',
                marginBottom: '28px',
                display: 'flex',
                justifyContent: 'space-between',
                alignItems: 'center',
                flexWrap: 'wrap',
                gap: '16px'
              }}>
                <div>
                  <h3 style={{ fontSize: '1.45rem', fontWeight: '800', marginBottom: '4px' }}>
                    {employee.name} — Adjudication Workspace
                  </h3>
                  <p style={{ color: '#cbd5e1', fontSize: '0.9rem' }}>
                    Employee ID: <strong style={{ color: '#93c5fd' }}>{employee.id}</strong> | Role: <strong style={{ color: '#ffffff' }}>{employee.role}</strong> | {employee.department}
                  </p>
                </div>
                <div style={{ display: 'flex', gap: '10px' }}>
                  <button
                    onClick={() => setActiveTab('Pending Claims')}
                    className="btn btn-primary"
                    style={{ backgroundColor: '#2563eb', borderColor: '#2563eb' }}
                  >
                    Review Pending Claims ({pendingClaims.length}) →
                  </button>
                </div>
              </div>

              {/* METRICS ROW (Total, pending, approved, rejected, pending reports) */}
              <div className="metrics-row" style={{ gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))' }}>
                <div className="metric-card">
                  <div className="metric-header">
                    <span className="metric-title">Total Claims</span>
                    <span className="metric-icon" style={{ background: '#f1f5f9', color: '#0b2545' }}>📑</span>
                  </div>
                  <div className="metric-val">{stats.totalClaims}</div>
                  <div className="metric-sub">Total claims recorded</div>
                </div>

                <div className="metric-card" style={{ borderColor: '#fde68a' }}>
                  <div className="metric-header">
                    <span className="metric-title">Pending Claims</span>
                    <span className="metric-icon" style={{ background: '#fffbeb', color: '#b45309' }}>⏳</span>
                  </div>
                  <div className="metric-val" style={{ color: '#b45309' }}>{stats.pendingClaims}</div>
                  <div className="metric-sub">Requires officer review</div>
                </div>

                <div className="metric-card" style={{ borderColor: '#a7f3d0' }}>
                  <div className="metric-header">
                    <span className="metric-title">Approved Claims</span>
                    <span className="metric-icon" style={{ background: '#ecfdf5', color: '#047857' }}>✅</span>
                  </div>
                  <div className="metric-val" style={{ color: '#047857' }}>{stats.approvedClaims}</div>
                  <div className="metric-sub">Settled & disbursed</div>
                </div>

                <div className="metric-card" style={{ borderColor: '#fecaca' }}>
                  <div className="metric-header">
                    <span className="metric-title">Rejected Claims</span>
                    <span className="metric-icon" style={{ background: '#fef2f2', color: '#b91c1c' }}>❌</span>
                  </div>
                  <div className="metric-val" style={{ color: '#b91c1c' }}>{stats.rejectedClaims}</div>
                  <div className="metric-sub">Liability denied / ineligible</div>
                </div>

                <div className="metric-card" style={{ borderColor: '#bfdbfe' }}>
                  <div className="metric-header">
                    <span className="metric-title">Pending Reports</span>
                    <span className="metric-icon" style={{ background: '#eff6ff', color: '#1d4ed8' }}>📊</span>
                  </div>
                  <div className="metric-val" style={{ color: '#1d4ed8' }}>{stats.pendingReports}</div>
                  <div className="metric-sub">Audits & compliance due</div>
                </div>
              </div>

              {/* ACTION REQUIRED: PENDING CLAIMS TABLE */}
              <div className="panel-card">
                <div className="panel-header">
                  <div>
                    <h3 className="panel-title">Urgent Adjudication Queue</h3>
                    <p style={{ fontSize: '0.825rem', color: '#64748b' }}>
                      Claims awaiting underwriter review and settlement validation
                    </p>
                  </div>
                  <button onClick={() => setActiveTab('Pending Claims')} className="btn btn-secondary btn-sm">
                    View Full Queue →
                  </button>
                </div>
                <div className="data-table-wrapper">
                  <table className="data-table">
                    <thead>
                      <tr>
                        <th>Claim ID</th>
                        <th>Claimant Name & ID</th>
                        <th>Policy Type</th>
                        <th>Claimed Amount</th>
                        <th>Submission Date</th>
                        <th>Priority</th>
                        <th>Action</th>
                      </tr>
                    </thead>
                    <tbody>
                      {pendingClaims.map((claim) => (
                        <tr key={claim.id}>
                          <td style={{ fontWeight: '700', color: '#0b2545' }}>{claim.id}</td>
                          <td>
                            <strong>{claim.claimantName}</strong>
                            <div style={{ fontSize: '0.775rem', color: '#64748b' }}>ID: {claim.claimantId}</div>
                          </td>
                          <td>{claim.policyType} ({claim.policyId})</td>
                          <td style={{ fontWeight: '700', color: '#0b2545' }}>{claim.amount}</td>
                          <td style={{ color: '#64748b' }}>{claim.submissionDate}</td>
                          <td>
                            <span className="badge" style={{
                              backgroundColor: claim.priority === 'Urgent' ? '#fef2f2' : '#fffbeb',
                              color: claim.priority === 'Urgent' ? '#b91c1c' : '#b45309',
                              border: `1px solid ${claim.priority === 'Urgent' ? '#fecaca' : '#fde68a'}`
                            }}>
                              {claim.priority}
                            </span>
                          </td>
                          <td>
                            <button
                              onClick={() => openReviewModal(claim)}
                              className="btn btn-primary btn-sm"
                            >
                              Review
                            </button>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              </div>

              {/* PENDING REPORTS SUMMARY */}
              <div className="panel-card">
                <div className="panel-header">
                  <h3 className="panel-title">Pending Actuarial & Compliance Reports ({pendingReports.length})</h3>
                  <button onClick={() => setActiveTab('Reports')} className="btn btn-secondary btn-sm">
                    View All Reports →
                  </button>
                </div>
                <div className="data-table-wrapper">
                  <table className="data-table">
                    <thead>
                      <tr>
                        <th>Report Code</th>
                        <th>Report Title</th>
                        <th>Department</th>
                        <th>Deadline</th>
                        <th>Status</th>
                      </tr>
                    </thead>
                    <tbody>
                      {pendingReports.slice(0, 3).map((rep) => (
                        <tr key={rep.id}>
                          <td style={{ fontWeight: '700', color: '#134074' }}>{rep.id}</td>
                          <td>{rep.title}</td>
                          <td>{rep.department}</td>
                          <td style={{ color: '#b45309', fontWeight: '600' }}>{rep.deadline}</td>
                          <td>
                            <span className="badge badge-pending">{rep.status}</span>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          )}

          {/* 2. PENDING CLAIMS VIEW */}
          {activeTab === 'Pending Claims' && (
            <div className="panel-card">
              <div className="panel-header">
                <div>
                  <h3 className="panel-title">Pending Claims for Officer Review ({pendingClaims.length})</h3>
                  <p style={{ fontSize: '0.85rem', color: '#64748b' }}>
                    Evaluate evidence, policy coverage limits, and approve or reject claims.
                  </p>
                </div>
                <span className="badge badge-pending">{pendingClaims.length} Awaiting Review</span>
              </div>
              <div className="data-table-wrapper">
                {pendingClaims.length === 0 ? (
                  <div style={{ padding: '48px', textAlign: 'center', color: '#64748b' }}>
                    <div style={{ fontSize: '2rem', marginBottom: '8px' }}>🎉</div>
                    <h4>All pending claims have been adjudicated!</h4>
                    <p style={{ fontSize: '0.9rem' }}>No further actions required in the queue.</p>
                  </div>
                ) : (
                  <table className="data-table">
                    <thead>
                      <tr>
                        <th>Claim Ref</th>
                        <th>Claimant Details</th>
                        <th>Policy</th>
                        <th>Amount</th>
                        <th>Date Filed</th>
                        <th>Category & Summary</th>
                        <th>Priority</th>
                        <th>Adjudication</th>
                      </tr>
                    </thead>
                    <tbody>
                      {pendingClaims.map((claim) => (
                        <tr key={claim.id}>
                          <td style={{ fontWeight: '800', color: '#0b2545' }}>{claim.id}</td>
                          <td>
                            <div style={{ fontWeight: '700' }}>{claim.claimantName}</div>
                            <div style={{ fontSize: '0.8rem', color: '#64748b' }}>Customer ID: {claim.claimantId}</div>
                          </td>
                          <td>
                            <div>{claim.policyType}</div>
                            <div style={{ fontSize: '0.8rem', color: '#64748b' }}>{claim.policyId}</div>
                          </td>
                          <td style={{ fontWeight: '700', fontSize: '0.95rem' }}>{claim.amount}</td>
                          <td style={{ color: '#64748b' }}>{claim.submissionDate}</td>
                          <td style={{ maxWidth: '280px' }}>
                            <div style={{ fontWeight: '600', color: '#0f172a' }}>{claim.category}</div>
                            <div style={{ fontSize: '0.825rem', color: '#64748b', whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis' }}>
                              {claim.description}
                            </div>
                          </td>
                          <td>
                            <span className="badge" style={{
                              backgroundColor: claim.priority === 'Urgent' ? '#fef2f2' : '#fffbeb',
                              color: claim.priority === 'Urgent' ? '#b91c1c' : '#b45309',
                              border: `1px solid ${claim.priority === 'Urgent' ? '#fecaca' : '#fde68a'}`
                            }}>
                              {claim.priority}
                            </span>
                          </td>
                          <td>
                            <button
                              onClick={() => openReviewModal(claim)}
                              className="btn btn-primary btn-sm"
                            >
                              Review
                            </button>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                )}
              </div>
            </div>
          )}

          {/* 3. ALL CLAIMS VIEW */}
          {activeTab === 'All Claims' && (
            <div className="panel-card">
              <div className="panel-header">
                <div>
                  <h3 className="panel-title">Master Claims Ledger</h3>
                  <p style={{ fontSize: '0.85rem', color: '#64748b' }}>Comprehensive repository of all historical and current filings</p>
                </div>
                <div style={{ display: 'flex', gap: '8px' }}>
                  <button onClick={() => alert('Exporting claims ledger as CSV...')} className="btn btn-secondary btn-sm">
                    ⬇️ Export CSV
                  </button>
                </div>
              </div>
              <div className="data-table-wrapper">
                <table className="data-table">
                  <thead>
                    <tr>
                      <th>Claim ID</th>
                      <th>Claimant (ID)</th>
                      <th>Policy Details</th>
                      <th>Claim Amount</th>
                      <th>Date Filed</th>
                      <th>Adjudication Status</th>
                    </tr>
                  </thead>
                  <tbody>
                    {allClaims.map((claim) => (
                      <tr key={claim.id}>
                        <td style={{ fontWeight: '700', color: '#0b2545' }}>{claim.id}</td>
                        <td>{claim.claimant}</td>
                        <td>{claim.policy}</td>
                        <td style={{ fontWeight: '600' }}>{claim.amount}</td>
                        <td style={{ color: '#64748b' }}>{claim.date}</td>
                        <td>
                          <span className={`badge ${
                            claim.status === 'Approved' ? 'badge-approved' :
                            claim.status === 'Pending' ? 'badge-pending' : 'badge-rejected'
                          }`}>
                            {claim.status}
                          </span>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          )}

          {/* 4. POLICY VERIFICATION VIEW */}
          {activeTab === 'Policy Verification' && (
            <div className="panel-card">
              <div className="panel-header">
                <h3 className="panel-title">Underwriting & Policy Verification Queue</h3>
                <span className="badge badge-navy">Active Registry</span>
              </div>
              <div style={{ padding: '24px' }}>
                <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
                  <div style={{ padding: '16px', background: '#f8fafc', border: '1px solid #e2e8f0', borderRadius: '8px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                    <div>
                      <div style={{ fontWeight: '700', color: '#0b2545' }}>POL-98214 — Comprehensive Health Shield</div>
                      <div style={{ fontSize: '0.85rem', color: '#64748b' }}>Policyholder: Sarah Jenkins (123) | Coverage: $500,000 | Underwritten by: Aegis Health Re</div>
                    </div>
                    <span className="badge badge-approved">Underwriting Verified</span>
                  </div>

                  <div style={{ padding: '16px', background: '#f8fafc', border: '1px solid #e2e8f0', borderRadius: '8px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                    <div>
                      <div style={{ fontWeight: '700', color: '#0b2545' }}>POL-55421 — Commercial Motor Fleet</div>
                      <div style={{ fontSize: '0.85rem', color: '#64748b' }}>Policyholder: David Miller (456) | Coverage: $850,000 | Telematics Endorsed</div>
                    </div>
                    <span className="badge badge-approved">Underwriting Verified</span>
                  </div>

                  <div style={{ padding: '16px', background: '#f8fafc', border: '1px solid #e2e8f0', borderRadius: '8px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                    <div>
                      <div style={{ fontWeight: '700', color: '#0b2545' }}>POL-33109 — Prestige Homeowners All-Risk</div>
                      <div style={{ fontSize: '0.85rem', color: '#64748b' }}>Policyholder: Elena Rostova (789) | Coverage: $750,000 | Annual Re-Inspection Passed</div>
                    </div>
                    <span className="badge badge-approved">Underwriting Verified</span>
                  </div>
                </div>
              </div>
            </div>
          )}

          {/* 5. CLAIM REVIEW WORKBENCH */}
          {activeTab === 'Claim Review' && (
            <div className="panel-card">
              <div className="panel-header">
                <h3 className="panel-title">Claim Adjudication Workbench</h3>
                <span className="badge badge-navy">Senior Underwriter Mode</span>
              </div>
              <div style={{ padding: '24px' }}>
                <p style={{ color: '#475569', marginBottom: '20px' }}>
                  Select any pending claim from the list below to review case evidence and record a binding determination:
                </p>
                <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(280px, 1fr))', gap: '16px' }}>
                  {pendingClaims.map(c => (
                    <div key={c.id} style={{ border: '1px solid #e2e8f0', padding: '16px', borderRadius: '8px', background: '#ffffff' }}>
                      <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '8px' }}>
                        <strong style={{ color: '#0b2545' }}>{c.id}</strong>
                        <span className="badge badge-pending">{c.priority}</span>
                      </div>
                      <div style={{ fontSize: '0.85rem', color: '#64748b', marginBottom: '4px' }}>
                        Claimant: {c.claimantName} ({c.claimantId})
                      </div>
                      <div style={{ fontSize: '0.9rem', fontWeight: '700', color: '#2563eb', marginBottom: '12px' }}>
                        {c.amount} • {c.category}
                      </div>
                      <button onClick={() => openReviewModal(c)} className="btn btn-primary btn-sm" style={{ width: '100%' }}>
                        Open Review Modal →
                      </button>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          )}

          {/* 6. REPORTS VIEW */}
          {activeTab === 'Reports' && (
            <div className="panel-card">
              <div className="panel-header">
                <div>
                  <h3 className="panel-title">Actuarial & Compliance Audits</h3>
                  <p style={{ fontSize: '0.85rem', color: '#64748b' }}>Regulatory submissions and loss ratio evaluations</p>
                </div>
                <span className="badge badge-navy">{pendingReports.length} Active Audits</span>
              </div>
              <div className="data-table-wrapper">
                <table className="data-table">
                  <thead>
                    <tr>
                      <th>Report Reference</th>
                      <th>Audit Title</th>
                      <th>Department</th>
                      <th>Submission Deadline</th>
                      <th>Priority</th>
                      <th>Status</th>
                      <th>Action</th>
                    </tr>
                  </thead>
                  <tbody>
                    {pendingReports.map(report => (
                      <tr key={report.id}>
                        <td style={{ fontWeight: '700', color: '#0b2545' }}>{report.id}</td>
                        <td style={{ fontWeight: '600' }}>{report.title}</td>
                        <td>{report.department}</td>
                        <td style={{ color: '#b45309', fontWeight: '600' }}>{report.deadline}</td>
                        <td>
                          <span className="badge" style={{
                            backgroundColor: report.priority === 'Urgent' ? '#fef2f2' : '#eff6ff',
                            color: report.priority === 'Urgent' ? '#b91c1c' : '#1d4ed8',
                            border: `1px solid ${report.priority === 'Urgent' ? '#fecaca' : '#bfdbfe'}`
                          }}>
                            {report.priority}
                          </span>
                        </td>
                        <td>
                          <span className="badge badge-pending">{report.status}</span>
                        </td>
                        <td>
                          <button
                            onClick={() => alert(`Opening audit dossier for ${report.id}...`)}
                            className="btn btn-secondary btn-sm"
                          >
                            Inspect Dossier
                          </button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          )}

          {/* 7. PROFILE VIEW */}
          {activeTab === 'Profile' && (
            <div className="panel-card" style={{ maxWidth: '800px', margin: '0 auto' }}>
              <div className="panel-header">
                <h3 className="panel-title">Underwriter Officer Credentials</h3>
                <span className="badge badge-approved">Active Duty</span>
              </div>
              <div style={{ padding: '28px' }}>
                <div style={{ display: 'flex', alignItems: 'center', gap: '20px', marginBottom: '28px' }}>
                  <div style={{
                    width: '64px',
                    height: '64px',
                    borderRadius: '50%',
                    backgroundColor: '#0b2545',
                    color: '#ffffff',
                    fontSize: '1.5rem',
                    fontWeight: '800',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center'
                  }}>
                    MV
                  </div>
                  <div>
                    <h4 style={{ fontSize: '1.25rem', fontWeight: '800', color: '#0b2545' }}>{employee.name}</h4>
                    <p style={{ color: '#64748b', fontSize: '0.9rem' }}>
                      Employee ID: <strong>{employee.id}</strong> • {employee.role}
                    </p>
                  </div>
                </div>

                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px' }}>
                  <div className="policy-detail-row">
                    <span className="label">Employee ID</span>
                    <span className="val">{employee.id}</span>
                  </div>
                  <div className="policy-detail-row">
                    <span className="label">Official Role</span>
                    <span className="val">{employee.role}</span>
                  </div>
                  <div className="policy-detail-row">
                    <span className="label">Division / Dept</span>
                    <span className="val">{employee.department}</span>
                  </div>
                  <div className="policy-detail-row">
                    <span className="label">Regional Headquarters</span>
                    <span className="val">{employee.office}</span>
                  </div>
                  <div className="policy-detail-row">
                    <span className="label">Underwriting Authority</span>
                    <span className="val" style={{ color: '#1d4ed8' }}>{employee.clearanceLevel}</span>
                  </div>
                  <div className="policy-detail-row">
                    <span className="label">Official Email</span>
                    <span className="val">{employee.email}</span>
                  </div>
                  <div className="policy-detail-row">
                    <span className="label">Monthly Settlement Cap</span>
                    <span className="val" style={{ color: '#047857' }}>{employee.stats.monthlySettlementVolume}</span>
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>
      </main>

      {/* CLAIM REVIEW MODAL */}
      {selectedClaimForReview && (
        <div className="modal-overlay">
          <div className="modal-content">
            <div className="modal-header">
              <div>
                <h3 className="modal-title">Adjudicate Claim: {selectedClaimForReview.id}</h3>
                <p style={{ fontSize: '0.85rem', color: '#64748b' }}>
                  Policyholder: {selectedClaimForReview.claimantName} (ID: {selectedClaimForReview.claimantId})
                </p>
              </div>
              <button onClick={closeReviewModal} style={{ fontSize: '1.25rem', color: '#64748b' }}>✕</button>
            </div>

            <div className="modal-body">
              {reviewSuccessMessage ? (
                <div className="badge badge-approved" style={{ display: 'block', padding: '16px', textAlign: 'center', fontSize: '1rem' }}>
                  {reviewSuccessMessage}
                </div>
              ) : (
                <>
                  <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '16px', background: '#f8fafc', padding: '16px', borderRadius: '8px' }}>
                    <div>
                      <div style={{ fontSize: '0.75rem', color: '#64748b', textTransform: 'uppercase', fontWeight: '700' }}>Policy Under Review</div>
                      <div style={{ fontWeight: '700', color: '#0b2545' }}>{selectedClaimForReview.policyType}</div>
                      <div style={{ fontSize: '0.8rem', color: '#64748b' }}>{selectedClaimForReview.policyId}</div>
                    </div>
                    <div>
                      <div style={{ fontSize: '0.75rem', color: '#64748b', textTransform: 'uppercase', fontWeight: '700' }}>Claimed Amount</div>
                      <div style={{ fontSize: '1.25rem', fontWeight: '800', color: '#2563eb' }}>{selectedClaimForReview.amount}</div>
                    </div>
                  </div>

                  <div>
                    <h5 style={{ fontSize: '0.85rem', color: '#475569', textTransform: 'uppercase', marginBottom: '6px', fontWeight: '700' }}>
                      Incident Category & Statement
                    </h5>
                    <div style={{ padding: '12px', background: '#ffffff', border: '1px solid #e2e8f0', borderRadius: '6px', fontSize: '0.9rem', color: '#0f172a' }}>
                      <strong>{selectedClaimForReview.category}:</strong> {selectedClaimForReview.description}
                    </div>
                  </div>

                  <div>
                    <h5 style={{ fontSize: '0.85rem', color: '#475569', textTransform: 'uppercase', marginBottom: '6px', fontWeight: '700' }}>
                      Submitted Evidence & Files
                    </h5>
                    <div style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
                      {selectedClaimForReview.documents.map((doc, idx) => (
                        <div key={idx} style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '8px 12px', background: '#f1f5f9', borderRadius: '6px', fontSize: '0.85rem' }}>
                          <span>📄 {doc}</span>
                          <button onClick={() => alert(`Viewing document ${doc}...`)} className="btn btn-secondary btn-sm" style={{ padding: '3px 8px', fontSize: '0.75rem' }}>
                            Preview
                          </button>
                        </div>
                      ))}
                    </div>
                  </div>

                  <div className="form-group">
                    <label>Underwriter Determination Note (Optional)</label>
                    <textarea
                      rows="3"
                      className="input-field"
                      placeholder="Add adjudication justification, policy exclusions verified, or settlement condition..."
                      value={reviewNote}
                      onChange={(e) => setReviewNote(e.target.value)}
                    ></textarea>
                  </div>
                </>
              )}
            </div>

            {!reviewSuccessMessage && (
              <div className="modal-footer">
                <button onClick={closeReviewModal} className="btn btn-secondary">
                  Cancel
                </button>
                <button
                  onClick={() => handleDecision('Rejected')}
                  className="btn btn-danger"
                >
                  Reject Claim
                </button>
                <button
                  onClick={() => handleDecision('Approved')}
                  className="btn btn-success"
                >
                  ✓ Approve Claim & Issue Settlement
                </button>
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
}
