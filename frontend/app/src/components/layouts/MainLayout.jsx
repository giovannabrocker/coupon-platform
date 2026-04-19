import React from 'react';
import Header from '../Header/Header';
import { Box, Container } from '@mui/material';

export default function Layout({ children }) {
  return (
    <>
      <Header />
      <Box component="main" sx={{ mt: 12 }}>
        <Container maxWidth="md">{children}</Container>
      </Box>
    </>
  );
}
