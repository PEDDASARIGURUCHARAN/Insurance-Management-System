import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function LoginPage({ onLoginSuccess }) {
  const [loginId, setLoginId] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  const handleLogin = (e) => {
    e.preventDefault();
    const trimmedId = loginId.trim();

    if (!trimmedId) {
      setErrorMessage('Please enter your Login ID.');
      return;
    }

    if (trimmedId === '123') {
      const user = { id: '123', role: 'customer' };
      if (onLoginSuccess) onLoginSuccess(user);
      setErrorMessage('');
      navigate('/customer');
    } else if (trimmedId === '2400030001') {
      const user = { id: '2400030001', role: 'admin' };
      if (onLoginSuccess) onLoginSuccess(user);
      setErrorMessage('');
      navigate('/admin');
    } else {
      setErrorMessage('Invalid Login ID');
    }
  };

  const handleQuickFill = (id) => {
    setLoginId(id);
    setErrorMessage('');
  };

  return (
    <div className="login-page">
      <div className="login-card">
        <div className="login-header">
          <div className="login-icon-badge">🛡️</div>
          <h2>Aegis Assurance Portal</h2>
          <p>Enter your authorized Login ID to continue</p>
        </div>

        {errorMessage && (
          <div className="error-banner" style={{ marginBottom: '20px' }}>
            <span>⚠️</span>
            <span>{errorMessage}</span>
          </div>
        )}

        <form onSubmit={handleLogin} className="login-form">
          <div className="form-group">
            <label htmlFor="login-id-input">Login ID</label>
            <input
              id="login-id-input"
              type="text"
              className="input-field"
              placeholder="Enter your Login ID"
              value={loginId}
              onChange={(e) => {
                setLoginId(e.target.value);
                if (errorMessage) setErrorMessage('');
              }}
              autoFocus
            />
          </div>

          <button id="submit-login-btn" type="submit" className="btn btn-primary" style={{ padding: '12px' }}>
            Sign In to Portal →
          </button>
        </form>

        <div className="test-credentials-box">
          <h4>Demo Test Credentials (Click to fill)</h4>
          <div className="cred-pills">
            <div className="cred-pill" onClick={() => handleQuickFill('123')}>
              <span>Customer Demo</span>
              <code>123</code>
            </div>
            <div className="cred-pill" onClick={() => handleQuickFill('2400030001')}>
              <span>Employee / Admin Demo</span>
              <code>2400030001</code>
            </div>
          </div>
        </div>

        <div style={{ textAlign: 'center', marginTop: '24px' }}>
          <button
            onClick={() => navigate('/')}
            className="btn btn-secondary btn-sm"
          >
            ← Return to Home Page
          </button>
        </div>
      </div>
    </div>
  );
}
