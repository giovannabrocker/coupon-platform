import React from 'react';
import { Container, Box, Paper } from '@mui/material';

export default function AuthLayout({ children }) {
  return (
    <Container maxWidth="xs">
      <Box
        sx={{
          minHeight: '100vh',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          padding: 2,
        }}
      >
        <Paper elevation={3} sx={{ padding: 4, width: '100%' }}>
          {children}
        </Paper>
      </Box>
    </Container>
  );
}
