import React from 'react';
import { AppBar, Toolbar, Typography, Button, Box } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';

export default function Header() {
  const navigate = useNavigate();
  const { logout } = useAuth();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  const handleGoHome = () => {
    navigate('/home');
  };

  const handleGoUsers = () => {
    navigate('/users');
  };

  const handleGoChangePassword = () => {
    navigate('/change-password');  
  };

  return (
    <AppBar position="fixed">
      <Toolbar>
        <Typography
          variant="h6"
          sx={{ flexGrow: 1, cursor: 'pointer' }}
          onClick={handleGoHome}
        >
          Spring Boot + React App
        </Typography>

        <Box sx={{ display: 'flex', gap: 2 }}>
          <Button color="inherit" onClick={handleGoHome}>
            Home
          </Button>
          <Button color="inherit" onClick={handleGoUsers}>
            Usuários
          </Button>
          <Button color="inherit" onClick={handleGoChangePassword}>
            Alterar Senha
          </Button>
          <Button color="inherit" onClick={handleLogout}>
            Sair
          </Button>
        </Box>
      </Toolbar>
    </AppBar>
  );
}
