import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { MOCK_CUSTOMERS } from '../mockData';

export default function CustomerDashboard({ onLogout }) {
  const navigate = useNavigate();
  const customer = MOCK_CUSTOMERS['123'];

  const [activeTab, setActiveTab] = useState('Dashboard');
  const [claimsList, setClaimsList] = useState(customer.claims);
  const [submissionSuccess, setSubmissionSuccess] = useState(false);

  // Claim form state
  const [newClaim, setNewClaim] = useState({
    policyId: customer.policies[0].id,
    amount: '',
    category: 'Medical Outpatient',
    details: ''
  });

  const handleLogout = () => {
    if (onLogout) onLogout();
    navigate('/');
  };

  const handleSubmitClaim = (e) => {
    e.preventDefault();
    if (!newClaim.amount || !newClaim.details) {
      alert('Please fill in the claim amount and details.');
      return;
    }

    const matchedPolicy = customer.policies.find(p => p.id === newClaim.policyId);
    const createdClaim = {
      id: `CLM-${Math.floor(1000 + Math.random() * 9000)}`,
      policyId: newClaim.policyId,
      policyName: matchedPolicy ? matchedPolicy.name : 'Standard Policy',
      amount: `$${parseFloat(newClaim.amount).toLocaleString('en-US', { minimumFractionDigits: 2 })}`,
      submittedDate: new Date().toISOString().split('T')[0],
      status: 'Pending',
      category: newClaim.category,
      details: newClaim.details,
      stage: 'Underwriter Review'
    };

    setClaimsList([createdClaim, ...claimsList]);
    setSubmissionSuccess(true);
    setNewClaim({
      policyId: customer.policies[0].id,
      amount: '',
      category: 'Medical Outpatient',
      details: ''
    });

    setTimeout(() => {
      setSubmissionSuccess(false);
      setActiveTab('My Claims');
    }, 1500);
  };

  // Calculate dynamic stats
  const totalClaimsCount = claimsList.length;
  const pendingClaimsCount = claimsList.filter(c => c.status === 'Pending').length;
  const approvedClaimsCount = claimsList.filter(c => c.status === 'Approved').length;

  return (
    <div className="dashboard-layout">
      {/* SIDEBAR */}
      <aside className="dashboard-sidebar">
        <div className="sidebar-header">
          <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="#60a5fa" strokeWidth="2">
            <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z" />
          </svg>
          <div>
            <div className="brand-title">AEGIS ASSURANCE</div>
            <div className="portal-badge">Customer Portal</div>
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
            className={`sidebar-nav-item ${activeTab === 'My Policies' ? 'active' : ''}`}
            onClick={() => setActiveTab('My Policies')}
          >
            <span>📄</span> My Policies
          </button>
          <button
            className={`sidebar-nav-item ${activeTab === 'Submit Claim' ? 'active' : ''}`}
            onClick={() => setActiveTab('Submit Claim')}
          >
            <span>➕</span> Submit Claim
          </button>
          <button
            className={`sidebar-nav-item ${activeTab === 'My Claims' ? 'active' : ''}`}
            onClick={() => setActiveTab('My Claims')}
          >
            <span>📑</span> My Claims
          </button>
          <button
            className={`sidebar-nav-item ${activeTab === 'Claim Status' ? 'active' : ''}`}
            onClick={() => setActiveTab('Claim Status')}
          >
            <span>⏱️</span> Claim Status
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
            id="customer-logout-btn"
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
              <div className="avatar">SJ</div>
              <div className="user-meta">
                <div className="user-name">{customer.name}</div>
                <div className="user-role">Customer ID: {customer.id}</div>
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
              {/* Profile Card Header */}
              <div style={{
                background: 'linear-gradient(135deg, #0b2545 0%, #134074 100%)',
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
                    Welcome back, {customer.name}
                  </h3>
                  <p style={{ color: '#cbd5e1', fontSize: '0.9rem' }}>
                    Customer ID: <strong style={{ color: '#93c5fd' }}>{customer.id}</strong> | {customer.tier} | Status: <span className="badge badge-approved" style={{ marginLeft: '4px' }}>Verified</span>
                  </p>
                </div>
                <button
                  onClick={() => setActiveTab('Submit Claim')}
                  className="btn btn-primary"
                  style={{ backgroundColor: '#2563eb', borderColor: '#2563eb' }}
                >
                  + File New Claim
                </button>
              </div>

              {/* METRICS ROW */}
              <div className="metrics-row">
                <div className="metric-card">
                  <div className="metric-header">
                    <span className="metric-title">Active Policies</span>
                    <span className="metric-icon" style={{ background: '#eff6ff', color: '#1d4ed8' }}>📄</span>
                  </div>
                  <div className="metric-val">{customer.policies.length}</div>
                  <div className="metric-sub">Fully protected policies</div>
                </div>

                <div className="metric-card">
                  <div className="metric-header">
                    <span className="metric-title">Total Claims</span>
                    <span className="metric-icon" style={{ background: '#f1f5f9', color: '#475569' }}>📑</span>
                  </div>
                  <div className="metric-val">{totalClaimsCount}</div>
                  <div className="metric-sub">Lifetime claim submissions</div>
                </div>

                <div className="metric-card">
                  <div className="metric-header">
                    <span className="metric-title">Pending Claims</span>
                    <span className="metric-icon" style={{ background: '#fffbeb', color: '#b45309' }}>⏳</span>
                  </div>
                  <div className="metric-val" style={{ color: '#b45309' }}>{pendingClaimsCount}</div>
                  <div className="metric-sub">Currently under review</div>
                </div>

                <div className="metric-card">
                  <div className="metric-header">
                    <span className="metric-title">Approved Claims</span>
                    <span className="metric-icon" style={{ background: '#ecfdf5', color: '#047857' }}>✅</span>
                  </div>
                  <div className="metric-val" style={{ color: '#047857' }}>{approvedClaimsCount}</div>
                  <div className="metric-sub">Successfully disbursed</div>
                </div>
              </div>

              {/* RECENT CLAIMS PANEL */}
              <div className="panel-card">
                <div className="panel-header">
                  <h3 className="panel-title">Recent Claims</h3>
                  <button onClick={() => setActiveTab('My Claims')} className="btn btn-secondary btn-sm">
                    View All Claims →
                  </button>
                </div>
                <div className="data-table-wrapper">
                  <table className="data-table">
                    <thead>
                      <tr>
                        <th>Claim ID</th>
                        <th>Policy</th>
                        <th>Amount</th>
                        <th>Date Filed</th>
                        <th>Category</th>
                        <th>Status</th>
                      </tr>
                    </thead>
                    <tbody>
                      {claimsList.slice(0, 3).map((claim) => (
                        <tr key={claim.id}>
                          <td style={{ fontWeight: '700', color: '#0b2545' }}>{claim.id}</td>
                          <td>{claim.policyName}</td>
                          <td style={{ fontWeight: '600' }}>{claim.amount}</td>
                          <td style={{ color: '#64748b' }}>{claim.submittedDate}</td>
                          <td>{claim.category}</td>
                          <td>
                            <span className={`badge ${claim.status === 'Approved' ? 'badge-approved' : 'badge-pending'}`}>
                              {claim.status}
                            </span>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              </div>

              {/* ACTIVE POLICIES QUICK VIEW */}
              <div className="panel-card">
                <div className="panel-header">
                  <h3 className="panel-title">Active Coverage Plans</h3>
                  <button onClick={() => setActiveTab('My Policies')} className="btn btn-secondary btn-sm">
                    Manage Policies →
                  </button>
                </div>
                <div style={{ padding: '24px' }}>
                  <div className="policies-grid">
                    {customer.policies.map((p) => (
                      <div key={p.id} className="policy-card-item">
                        <div className="policy-num">{p.id} • {p.category}</div>
                        <h4>{p.name}</h4>
                        <div className="policy-detail-row">
                          <span className="label">Total Coverage</span>
                          <span className="val">{p.coverage}</span>
                        </div>
                        <div className="policy-detail-row">
                          <span className="label">Deductible</span>
                          <span className="val">{p.deductible}</span>
                        </div>
                        <div className="policy-detail-row">
                          <span className="label">Premium</span>
                          <span className="val">{p.premium}</span>
                        </div>
                        <div className="policy-detail-row">
                          <span className="label">Status</span>
                          <span className="badge badge-active">{p.status}</span>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>
              </div>
            </div>
          )}

          {/* 2. MY POLICIES VIEW */}
          {activeTab === 'My Policies' && (
            <div>
              <div className="panel-card">
                <div className="panel-header">
                  <h3 className="panel-title">My Insurance Policies ({customer.policies.length})</h3>
                  <span className="badge badge-navy">All Active</span>
                </div>
                <div style={{ padding: '24px' }}>
                  <div className="policies-grid">
                    {customer.policies.map((policy) => (
                      <div key={policy.id} className="policy-card-item">
                        <div className="policy-num">{policy.id}</div>
                        <h4>{policy.name}</h4>
                        <p style={{ color: '#64748b', fontSize: '0.85rem', marginBottom: '16px' }}>
                          Category: <strong>{policy.category}</strong> | Network: <strong>{policy.network}</strong>
                        </p>
                        <div className="policy-detail-row">
                          <span className="label">Coverage Limit</span>
                          <span className="val">{policy.coverage}</span>
                        </div>
                        <div className="policy-detail-row">
                          <span className="label">Deductible</span>
                          <span className="val">{policy.deductible}</span>
                        </div>
                        <div className="policy-detail-row">
                          <span className="label">Premium Cost</span>
                          <span className="val">{policy.premium}</span>
                        </div>
                        <div className="policy-detail-row">
                          <span className="label">Renewal Date</span>
                          <span className="val">{policy.expiryDate}</span>
                        </div>
                        <div className="policy-detail-row">
                          <span className="label">Status</span>
                          <span className="badge badge-approved">{policy.status}</span>
                        </div>
                        <div style={{ marginTop: '16px' }}>
                          <button
                            onClick={() => alert(`Downloading policy document for ${policy.id}...`)}
                            className="btn btn-secondary btn-sm"
                            style={{ width: '100%' }}
                          >
                            ⬇️ Download Schedule & Terms
                          </button>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>
              </div>
            </div>
          )}

          {/* 3. SUBMIT CLAIM VIEW */}
          {activeTab === 'Submit Claim' && (
            <div className="panel-card" style={{ maxWidth: '750px', margin: '0 auto' }}>
              <div className="panel-header">
                <h3 className="panel-title">File an Insurance Claim</h3>
                <span className="badge badge-navy">Direct Submission</span>
              </div>
              <div style={{ padding: '28px' }}>
                {submissionSuccess && (
                  <div className="badge badge-approved" style={{ display: 'block', padding: '14px', marginBottom: '20px', textAlign: 'center', fontSize: '0.9rem' }}>
                    ✓ Claim submitted successfully! Forwarding to underwriter review queue...
                  </div>
                )}

                <form onSubmit={handleSubmitClaim} style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
                  <div className="form-group">
                    <label>Select Covered Policy</label>
                    <select
                      className="input-field"
                      value={newClaim.policyId}
                      onChange={(e) => setNewClaim({ ...newClaim, policyId: e.target.value })}
                    >
                      {customer.policies.map((p) => (
                        <option key={p.id} value={p.id}>
                          {p.name} ({p.id}) - Max {p.coverage}
                        </option>
                      ))}
                    </select>
                  </div>

                  <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '16px' }}>
                    <div className="form-group">
                      <label>Claim Amount ($ USD)</label>
                      <input
                        type="number"
                        className="input-field"
                        placeholder="e.g. 1450.00"
                        value={newClaim.amount}
                        onChange={(e) => setNewClaim({ ...newClaim, amount: e.target.value })}
                        required
                      />
                    </div>
                    <div className="form-group">
                      <label>Incident Category</label>
                      <select
                        className="input-field"
                        value={newClaim.category}
                        onChange={(e) => setNewClaim({ ...newClaim, category: e.target.value })}
                      >
                        <option value="Medical Outpatient">Medical Outpatient</option>
                        <option value="Hospital Inpatient">Hospital Inpatient</option>
                        <option value="Vehicle Damage">Vehicle Damage / Collision</option>
                        <option value="Home & Property Damage">Home & Property Damage</option>
                        <option value="Theft or Loss">Theft or Loss</option>
                      </select>
                    </div>
                  </div>

                  <div className="form-group">
                    <label>Incident Description & Justification</label>
                    <textarea
                      rows="4"
                      className="input-field"
                      placeholder="Please provide details of the incident, dates, treating facility or repair garage..."
                      value={newClaim.details}
                      onChange={(e) => setNewClaim({ ...newClaim, details: e.target.value })}
                      required
                    ></textarea>
                  </div>

                  <div className="form-group">
                    <label>Supporting Invoices or Proof Documents (Mock Upload)</label>
                    <div style={{
                      border: '2px dashed #cbd5e1',
                      borderRadius: '8px',
                      padding: '24px',
                      textAlign: 'center',
                      backgroundColor: '#f8fafc'
                    }}>
                      <div style={{ fontSize: '1.75rem', marginBottom: '8px' }}>📎</div>
                      <div style={{ fontSize: '0.875rem', color: '#475569', fontWeight: '600' }}>
                        Drag & drop hospital invoices, police reports, or damage photos
                      </div>
                      <div style={{ fontSize: '0.75rem', color: '#94a3b8', marginTop: '4px' }}>
                        PDF, PNG, JPG accepted (Up to 25MB)
                      </div>
                    </div>
                  </div>

                  <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '12px', marginTop: '12px' }}>
                    <button type="button" onClick={() => setActiveTab('Dashboard')} className="btn btn-secondary">
                      Cancel
                    </button>
                    <button type="submit" className="btn btn-primary">
                      Submit Claim for Adjudication →
                    </button>
                  </div>
                </form>
              </div>
            </div>
          )}

          {/* 4. MY CLAIMS VIEW */}
          {activeTab === 'My Claims' && (
            <div className="panel-card">
              <div className="panel-header">
                <h3 className="panel-title">All Submitted Claims ({claimsList.length})</h3>
                <button onClick={() => setActiveTab('Submit Claim')} className="btn btn-primary btn-sm">
                  + New Claim
                </button>
              </div>
              <div className="data-table-wrapper">
                <table className="data-table">
                  <thead>
                    <tr>
                      <th>Claim Reference</th>
                      <th>Policy</th>
                      <th>Claimed Amount</th>
                      <th>Date Filed</th>
                      <th>Category</th>
                      <th>Workflow Stage</th>
                      <th>Status</th>
                    </tr>
                  </thead>
                  <tbody>
                    {claimsList.map((claim) => (
                      <tr key={claim.id}>
                        <td style={{ fontWeight: '700', color: '#0b2545' }}>{claim.id}</td>
                        <td>{claim.policyName}</td>
                        <td style={{ fontWeight: '600' }}>{claim.amount}</td>
                        <td style={{ color: '#64748b' }}>{claim.submittedDate}</td>
                        <td>{claim.category}</td>
                        <td style={{ fontSize: '0.85rem', color: '#475569' }}>{claim.stage}</td>
                        <td>
                          <span className={`badge ${claim.status === 'Approved' ? 'badge-approved' : 'badge-pending'}`}>
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

          {/* 5. CLAIM STATUS TRACKER */}
          {activeTab === 'Claim Status' && (
            <div>
              <div className="panel-card">
                <div className="panel-header">
                  <h3 className="panel-title">Active Claim Tracking: CLM-8042</h3>
                  <span className="badge badge-pending">Underwriter Review</span>
                </div>
                <div style={{ padding: '32px' }}>
                  <div style={{ marginBottom: '24px', background: '#f8fafc', padding: '20px', borderRadius: '10px', border: '1px solid #e2e8f0' }}>
                    <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(180px, 1fr))', gap: '16px' }}>
                      <div>
                        <div style={{ fontSize: '0.75rem', color: '#64748b', textTransform: 'uppercase', fontWeight: '700' }}>Claim ID</div>
                        <div style={{ fontSize: '1rem', fontWeight: '700', color: '#0b2545' }}>CLM-8042</div>
                      </div>
                      <div>
                        <div style={{ fontSize: '0.75rem', color: '#64748b', textTransform: 'uppercase', fontWeight: '700' }}>Policy</div>
                        <div style={{ fontSize: '1rem', fontWeight: '600' }}>Comprehensive Health Shield</div>
                      </div>
                      <div>
                        <div style={{ fontSize: '0.75rem', color: '#64748b', textTransform: 'uppercase', fontWeight: '700' }}>Amount</div>
                        <div style={{ fontSize: '1rem', fontWeight: '700', color: '#2563eb' }}>$3,450.00</div>
                      </div>
                      <div>
                        <div style={{ fontSize: '0.75rem', color: '#64748b', textTransform: 'uppercase', fontWeight: '700' }}>Assigned Officer</div>
                        <div style={{ fontSize: '1rem', fontWeight: '600' }}>Marcus Vance (ID: 2400030001)</div>
                      </div>
                    </div>
                  </div>

                  {/* Stepper tracker */}
                  <div style={{ display: 'flex', flexDirection: 'column', gap: '20px', marginTop: '32px' }}>
                    <div style={{ display: 'flex', gap: '16px', alignItems: 'flex-start' }}>
                      <div style={{ width: '32px', height: '32px', borderRadius: '50%', background: '#047857', color: '#ffffff', display: 'flex', alignItems: 'center', justifyContent: 'center', fontWeight: 'bold' }}>✓</div>
                      <div>
                        <div style={{ fontWeight: '700', color: '#0b2545' }}>Step 1: Digital Claim Submitted</div>
                        <div style={{ fontSize: '0.85rem', color: '#64748b' }}>Completed on Aug 18, 2026. Electronic filing verified.</div>
                      </div>
                    </div>

                    <div style={{ display: 'flex', gap: '16px', alignItems: 'flex-start' }}>
                      <div style={{ width: '32px', height: '32px', borderRadius: '50%', background: '#047857', color: '#ffffff', display: 'flex', alignItems: 'center', justifyContent: 'center', fontWeight: 'bold' }}>✓</div>
                      <div>
                        <div style={{ fontWeight: '700', color: '#0b2545' }}>Step 2: Medical Document Verification</div>
                        <div style={{ fontSize: '0.85rem', color: '#64748b' }}>Completed on Aug 19, 2026. Invoices cross-checked against PPO network fee schedules.</div>
                      </div>
                    </div>

                    <div style={{ display: 'flex', gap: '16px', alignItems: 'flex-start' }}>
                      <div style={{ width: '32px', height: '32px', borderRadius: '50%', background: '#b45309', color: '#ffffff', display: 'flex', alignItems: 'center', justifyContent: 'center', fontWeight: 'bold' }}>3</div>
                      <div>
                        <div style={{ fontWeight: '700', color: '#b45309' }}>Step 3: Senior Underwriter Adjudication (Current Stage)</div>
                        <div style={{ fontSize: '0.85rem', color: '#64748b' }}>Assigned to Senior Officer Marcus Vance for final coverage determination and liability limit signing.</div>
                      </div>
                    </div>

                    <div style={{ display: 'flex', gap: '16px', alignItems: 'flex-start', opacity: 0.5 }}>
                      <div style={{ width: '32px', height: '32px', borderRadius: '50%', background: '#cbd5e1', color: '#475569', display: 'flex', alignItems: 'center', justifyContent: 'center', fontWeight: 'bold' }}>4</div>
                      <div>
                        <div style={{ fontWeight: '700', color: '#475569' }}>Step 4: ACH Settlement & Disbursement</div>
                        <div style={{ fontSize: '0.85rem', color: '#64748b' }}>Direct deposit into your registered bank account upon adjudication approval.</div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          )}

          {/* 6. PROFILE VIEW */}
          {activeTab === 'Profile' && (
            <div className="panel-card" style={{ maxWidth: '800px', margin: '0 auto' }}>
              <div className="panel-header">
                <h3 className="panel-title">Policyholder Profile Information</h3>
                <span className="badge badge-approved">KYC Verified</span>
              </div>
              <div style={{ padding: '28px' }}>
                <div style={{ display: 'flex', alignItems: 'center', gap: '20px', marginBottom: '28px' }}>
                  <div style={{
                    width: '64px',
                    height: '64px',
                    borderRadius: '50%',
                    backgroundColor: '#eff6ff',
                    color: '#0b2545',
                    fontSize: '1.5rem',
                    fontWeight: '800',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    border: '2px solid #bfdbfe'
                  }}>
                    SJ
                  </div>
                  <div>
                    <h4 style={{ fontSize: '1.25rem', fontWeight: '800', color: '#0b2545' }}>{customer.name}</h4>
                    <p style={{ color: '#64748b', fontSize: '0.9rem' }}>
                      Customer ID: <strong>{customer.id}</strong> • Member since {customer.memberSince}
                    </p>
                  </div>
                </div>

                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px' }}>
                  <div className="policy-detail-row">
                    <span className="label">Full Legal Name</span>
                    <span className="val">{customer.name}</span>
                  </div>
                  <div className="policy-detail-row">
                    <span className="label">Registered Email</span>
                    <span className="val">{customer.email}</span>
                  </div>
                  <div className="policy-detail-row">
                    <span className="label">Primary Phone</span>
                    <span className="val">{customer.phone}</span>
                  </div>
                  <div className="policy-detail-row">
                    <span className="label">Residential Address</span>
                    <span className="val">{customer.address}</span>
                  </div>
                  <div className="policy-detail-row">
                    <span className="label">Membership Tier</span>
                    <span className="val">{customer.tier}</span>
                  </div>
                  <div className="policy-detail-row">
                    <span className="label">Total Settled Benefits</span>
                    <span className="val" style={{ color: '#047857' }}>{customer.stats.settledAmount}</span>
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>
      </main>
    </div>
  );
}
