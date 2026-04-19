import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { useSnackbar } from '../contexts/SnackbarContext';

export default function useLogin() {
  const { login } = useAuth(); 
  const { showSnackbar } = useSnackbar();
  const navigate = useNavigate();

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      const success = await login(email, password);
      if (success) {
        showSnackbar('Login realizado com sucesso!', 'success');
        navigate('/home');
      } else {
        showSnackbar('Credenciais inválidas. Tente novamente.', 'error');
      }
    } catch (err) {
      showSnackbar(err.message || 'Erro ao fazer login.', 'error');
    } finally {
      setLoading(false);
    }
  };

  return {
    email,
    password,
    setEmail,
    setPassword,
    handleLogin,
    loading,
  };
}
