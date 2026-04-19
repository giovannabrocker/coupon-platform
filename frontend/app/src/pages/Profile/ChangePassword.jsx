import React from 'react';
import { Box, Button, TextField, Typography, InputAdornment, IconButton } from '@mui/material';
import { Visibility, VisibilityOff } from '@mui/icons-material';
import { useChangePassword } from '../../hooks/useChangePassword';
import Layout from '../../components/layouts/MainLayout';

export default function ChangePassword() {
  const {
    currentPassword, setCurrentPassword,
    newPassword, setNewPassword,
    confirmPassword, setConfirmPassword,
    currentPassVisibility, newPassVisibility, confirmPassVisibility,
    loading, handleSubmit,
  } = useChangePassword();

  const renderAdornment = (visibilityHook) => (
    <InputAdornment position="end">
      <IconButton onClick={visibilityHook.toggle} edge="end">
        {visibilityHook.visible ? <VisibilityOff /> : <Visibility />}
      </IconButton>
    </InputAdornment>
  );

  return (
    <Layout>
      <Box component="form" onSubmit={handleSubmit} noValidate sx={{ maxWidth: 400, mx: 'auto', mt: 4 }}>
        <Typography variant="h5" gutterBottom>Alterar Senha</Typography>

        <TextField
          label="Senha Atual"
          type={currentPassVisibility.visible ? 'text' : 'password'}
          fullWidth
          margin="normal"
          required
          value={currentPassword}
          onChange={(e) => setCurrentPassword(e.target.value)}
          InputProps={{ endAdornment: renderAdornment(currentPassVisibility) }}
        />

        <TextField
          label="Nova Senha"
          type={newPassVisibility.visible ? 'text' : 'password'}
          fullWidth
          margin="normal"
          required
          value={newPassword}
          onChange={(e) => setNewPassword(e.target.value)}
          InputProps={{ endAdornment: renderAdornment(newPassVisibility) }}
        />

        <TextField
          label="Confirme a nova senha"
          type={confirmPassVisibility.visible ? 'text' : 'password'}
          fullWidth
          margin="normal"
          required
          value={confirmPassword}
          onChange={(e) => setConfirmPassword(e.target.value)}
          InputProps={{ endAdornment: renderAdornment(confirmPassVisibility) }}
        />

        <Button type="submit" fullWidth variant="contained" sx={{ mt: 3 }} disabled={loading}>
          {loading ? 'Alterando...' : 'Alterar Senha'}
        </Button>
      </Box>
    </Layout>
  );
}
