import React, { useState } from 'react';
import {
  Box,
  Button,
  TextField,
  Typography,
  FormControl,
  InputLabel,
  Select,
  MenuItem
} from '@mui/material';
import { useSnackbar } from '../../contexts/SnackbarContext';
import { useNavigate } from 'react-router-dom';
import AuthLink from '../../components/Auth/AuthLink';
import AuthLayout from '../../components/layouts/AuthLayout';
import useRegister from '../../hooks/useRegister';

export default function Register() {
  const { showSnackbar } = useSnackbar();
  const navigate = useNavigate();
  const { register, loading } = useRegister();

  const [cpf, setCpf] = useState('');
  const [role, setRole] = useState('USER'); // valor padrão
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleRegister = async (e) => {
    e.preventDefault();

    if (!name || !email || !password || !cpf || !role) {
      showSnackbar('Preencha todos os campos.', 'warning');
      return;
    }

    try {
      await register({ name, email, password, cpf, role });
      showSnackbar('Usuário cadastrado com sucesso!', 'success');
      navigate('/login');
    } catch (err) {
      showSnackbar(err.message, 'error');
    }

  };

  return (
    <AuthLayout>
      <Typography variant="h5" align="center" gutterBottom>
        Criar Conta
      </Typography>
      <Box component="form" onSubmit={handleRegister} noValidate>
        <TextField
          fullWidth
          margin="normal"
          label="Nome"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />
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
          label="CPF"
          value={cpf}
          onChange={(e) => setCpf(e.target.value)}
          required
        />

        <FormControl fullWidth margin="normal" required>
          <InputLabel id="role-label">Perfil</InputLabel>
          <Select
            labelId="role-label"
            value={role}
            label="Perfil"
            onChange={(e) => setRole(e.target.value)}
          >
            <MenuItem value="USER">Usuário</MenuItem>
            <MenuItem value="ADMIN">Administrador</MenuItem>
          </Select>
        </FormControl>
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
          disabled={loading}
        >
          {loading ? 'Cadastrando...' : 'Cadastrar'}
        </Button>
        <AuthLink
          question="Já tem uma conta?"
          linkText="Entrar"
          to="/login"
        />
      </Box>
    </AuthLayout>
  );
}
