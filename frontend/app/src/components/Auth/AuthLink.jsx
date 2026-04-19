import React from 'react';
import { Typography } from '@mui/material';
import { Link } from 'react-router-dom';

export default function AuthLink({ question, linkText, to }) {
  return (
    <Typography variant="body2" align="center" sx={{ mt: 2 }}>
      {question}{' '}
      <Link to={to} style={{ color: '#1976d2', textDecoration: 'none' }}>
        {linkText}
      </Link>
    </Typography>
  );
}
