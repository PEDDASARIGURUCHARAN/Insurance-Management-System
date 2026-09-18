export const MOCK_CUSTOMERS = {
  '123': {
    id: '123',
    name: 'Sarah Jenkins',
    email: 'sarah.jenkins@aegis-client.com',
    phone: '+1 (555) 234-8901',
    address: '742 Evergreen Terrace, Springfield, IL',
    memberSince: 'March 2021',
    tier: 'Gold Tier Policyholder',
    stats: {
      activePolicies: 3,
      totalClaims: 4,
      pendingClaims: 1,
      approvedClaims: 3,
      settledAmount: '$7,620.00'
    },
    policies: [
      {
        id: 'POL-98214',
        name: 'Comprehensive Health Shield',
        category: 'Health',
        coverage: '$500,000',
        deductible: '$1,000',
        premium: '$420 / mo',
        status: 'Active',
        expiryDate: 'Nov 15, 2026',
        network: 'Global PPO Preferred'
      },
      {
        id: 'POL-88412',
        name: 'Prime Executive Motor Guard',
        category: 'Auto',
        coverage: '$85,000',
        deductible: '$500',
        premium: '$165 / mo',
        status: 'Active',
        expiryDate: 'Jan 28, 2027',
        network: 'Certified Repair Network'
      },
      {
        id: 'POL-77103',
        name: 'Prestige Home & Property Shield',
        category: 'Property',
        coverage: '$750,000',
        deductible: '$2,500',
        premium: '$890 / yr',
        status: 'Active',
        expiryDate: 'Mar 10, 2027',
        network: 'All-Risk Premier'
      }
    ],
    claims: [
      {
        id: 'CLM-8042',
        policyId: 'POL-98214',
        policyName: 'Comprehensive Health Shield',
        amount: '$3,450.00',
        submittedDate: '2026-08-18',
        status: 'Pending',
        category: 'Outpatient Surgery & Diagnostics',
        details: 'Endoscopic evaluation and related outpatient diagnostics at Springfield General.',
        stage: 'Underwriter Review'
      },
      {
        id: 'CLM-7619',
        policyId: 'POL-88412',
        policyName: 'Prime Executive Motor Guard',
        amount: '$1,200.00',
        submittedDate: '2026-05-12',
        status: 'Approved',
        category: 'Road Debris Impact',
        details: 'Windshield replacement and sensor recalibration.',
        stage: 'Settlement Issued'
      },
      {
        id: 'CLM-6401',
        policyId: 'POL-98214',
        policyName: 'Comprehensive Health Shield',
        amount: '$820.00',
        submittedDate: '2026-02-04',
        status: 'Approved',
        category: 'Emergency Treatment',
        details: 'Urgent care consultation and prescription treatment.',
        stage: 'Settlement Issued'
      },
      {
        id: 'CLM-5110',
        policyId: 'POL-77103',
        policyName: 'Prestige Home & Property Shield',
        amount: '$5,600.00',
        submittedDate: '2025-11-20',
        status: 'Approved',
        category: 'Water Damage Restoration',
        details: 'Interior water leak repair and drying verification.',
        stage: 'Settlement Issued'
      }
    ]
  }
};

export const MOCK_EMPLOYEES = {
  '2400030001': {
    id: '2400030001',
    name: 'Marcus Vance',
    role: 'Senior Claims Officer & Underwriter',
    department: 'Risk Assessment & Claims Adjudication',
    office: 'Chicago Regional Headquarters (Floor 14)',
    clearanceLevel: 'Level 3 Executive Authority',
    email: 'm.vance@aegis-assurance.com',
    stats: {
      totalClaims: 184,
      pendingClaims: 8,
      approvedClaims: 154,
      rejectedClaims: 22,
      pendingReports: 5,
      monthlySettlementVolume: '$1.84M'
    },
    pendingClaims: [
      {
        id: 'CLM-8042',
        claimantName: 'Sarah Jenkins',
        claimantId: '123',
        policyId: 'POL-98214',
        policyType: 'Health Shield',
        amount: '$3,450.00',
        submissionDate: '2026-08-18',
        category: 'Medical Procedure',
        description: 'Endoscopic surgical evaluation and outpatient hospital fees at Springfield General.',
        priority: 'High',
        documents: ['Discharge_Summary.pdf', 'Hospital_Bill_Itemized.pdf'],
        status: 'Pending'
      },
      {
        id: 'CLM-8049',
        claimantName: 'David Miller',
        claimantId: '456',
        policyId: 'POL-55421',
        policyType: 'Commercial Fleet',
        amount: '$8,900.00',
        submissionDate: '2026-08-20',
        category: 'Collision & Freight Impact',
        description: 'Multi-vehicle collision on Interstate 55. Front axle damage and cargo securement failure.',
        priority: 'Urgent',
        documents: ['Police_Report_IL2408.pdf', 'Telematics_Telemetry.csv', 'Repair_Estimate.pdf'],
        status: 'Pending'
      },
      {
        id: 'CLM-8053',
        claimantName: 'Elena Rostova',
        claimantId: '789',
        policyId: 'POL-33109',
        policyType: 'Homeowners Premier',
        amount: '$12,400.00',
        submissionDate: '2026-08-22',
        category: 'Catastrophic Wind & Hail',
        description: 'Severe hail storm roof membrane perforation and secondary water intrusion in attic.',
        priority: 'Normal',
        documents: ['Drone_Inspection_Imagery.zip', 'Roofing_Contractor_Bid.pdf'],
        status: 'Pending'
      },
      {
        id: 'CLM-8061',
        claimantName: 'James Thornton',
        claimantId: '321',
        policyId: 'POL-99201',
        policyType: 'Health Shield',
        amount: '$1,850.00',
        submissionDate: '2026-08-24',
        category: 'Dental Trauma',
        description: 'Accidental maxillofacial dental injury requiring crowns and surgical repair.',
        priority: 'Normal',
        documents: ['Dental_Operative_Record.pdf', 'Itemized_Rx_Receipt.pdf'],
        status: 'Pending'
      }
    ],
    allClaims: [
      {
        id: 'CLM-8042',
        claimant: 'Sarah Jenkins (123)',
        policy: 'Health Shield (POL-98214)',
        amount: '$3,450.00',
        date: '2026-08-18',
        status: 'Pending'
      },
      {
        id: 'CLM-8049',
        claimant: 'David Miller (456)',
        policy: 'Commercial Fleet (POL-55421)',
        amount: '$8,900.00',
        date: '2026-08-20',
        status: 'Pending'
      },
      {
        id: 'CLM-8053',
        claimant: 'Elena Rostova (789)',
        policy: 'Homeowners Premier (POL-33109)',
        amount: '$12,400.00',
        date: '2026-08-22',
        status: 'Pending'
      },
      {
        id: 'CLM-8061',
        claimant: 'James Thornton (321)',
        policy: 'Health Shield (POL-99201)',
        amount: '$1,850.00',
        date: '2026-08-24',
        status: 'Pending'
      },
      {
        id: 'CLM-7988',
        claimant: 'Arthur Pendelton (812)',
        policy: 'Life & Annuity (POL-11002)',
        amount: '$50,000.00',
        date: '2026-08-10',
        status: 'Approved'
      },
      {
        id: 'CLM-7965',
        claimant: 'Sophia Martinez (551)',
        policy: 'Motor Guard (POL-88122)',
        amount: '$4,100.00',
        date: '2026-08-05',
        status: 'Approved'
      },
      {
        id: 'CLM-7890',
        claimant: 'Brian O’Connor (604)',
        policy: 'Marine Freight (POL-44190)',
        amount: '$18,500.00',
        date: '2026-07-28',
        status: 'Rejected'
      },
      {
        id: 'CLM-7812',
        claimant: 'Clara Oswald (219)',
        policy: 'Commercial Liability (POL-67011)',
        amount: '$6,250.00',
        date: '2026-07-15',
        status: 'Approved'
      }
    ],
    pendingReports: [
      {
        id: 'REP-2026-Q3-01',
        title: 'Q3 Enterprise Loss Ratio & Solvency Risk Review',
        department: 'Actuarial & Solvency',
        deadline: 'Sept 15, 2026',
        priority: 'High',
        status: 'Pending Approval'
      },
      {
        id: 'REP-2026-M08',
        title: 'Monthly Auto Casualty & Subrogation Recovery Analysis',
        department: 'Claims Recovery',
        deadline: 'Sept 05, 2026',
        priority: 'Urgent',
        status: 'Draft Ready'
      },
      {
        id: 'REP-2026-SIU-04',
        title: 'Special Investigation Unit (SIU) Fraud Analytics Audit',
        department: 'Integrity & Compliance',
        deadline: 'Sept 10, 2026',
        priority: 'High',
        status: 'Under Review'
      },
      {
        id: 'REP-2026-GOV-02',
        title: 'State Regulatory Solvency & Consumer Complaints Audit',
        department: 'Government Affairs',
        deadline: 'Sept 20, 2026',
        priority: 'Medium',
        status: 'Drafting'
      },
      {
        id: 'REP-2026-HCP-08',
        title: 'Medical Provider Fee Schedule & Hospital Network Audit',
        department: 'Health Network Contracting',
        deadline: 'Sept 12, 2026',
        priority: 'Medium',
        status: 'Data Ingestion'
      }
    ]
  }
};
