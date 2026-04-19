import React from 'react';
import {
  Box,
  Button,
  TextField,
  Typography
} from '@mui/material';
import AuthLayout from '../../components/layouts/AuthLayout';
import AuthLink from '../../components/Auth/AuthLink';
import useLogin from '../../hooks/useLogin';

export default function Login() {
  const {
    email,
    password,
    setEmail,
    setPassword,
    handleLogin
  } = useLogin();

  return (
    <AuthLayout>
      <Typography variant="h5" align="center" gutterBottom>
        Login
      </Typography>
      <Box component="form" onSubmit={handleLogin} noValidate>
        <TextField
          fullWidth
          margin="normal"
          label="E-mail"
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <TextField
          fullWidth
          margin="normal"
          label="Senha"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <Button
          type="submit"
          fullWidth
          variant="contained"
          sx={{ mt: 3 }}
        >
          Entrar
        </Button>
        <AuthLink
          question="Ainda não tem uma conta?"
          linkText="Criar conta"
          to="/signup"
        />
      </Box>
    </AuthLayout>
  );
}
