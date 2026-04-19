import { useState } from 'react';
import { changePassword as apiChangePassword } from '../services/apiUser';
import { useSnackbar } from '../contexts/SnackbarContext';
import { changePasswordSchema } from '../utils/validators/changePasswordSchema';
import { usePasswordVisibility } from './usePasswordVisibility';

export function useChangePassword() {
  const { showSnackbar } = useSnackbar();

  const [currentPassword, setCurrentPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  const currentPassVisibility = usePasswordVisibility();
  const newPassVisibility = usePasswordVisibility();
  const confirmPassVisibility = usePasswordVisibility();

  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await changePasswordSchema.validate({ currentPassword, newPassword, confirmPassword });
    } catch (validationError) {
      showSnackbar(validationError.message, 'warning');
      return;
    }

    if (newPassword !== confirmPassword) {
      showSnackbar('A nova senha e a confirmação não coincidem.', 'warning');
      return;
    }

    setLoading(true);
    try {
      await apiChangePassword(currentPassword, newPassword);
      showSnackbar('Senha alterada com sucesso!', 'success');
      setCurrentPassword('');
      setNewPassword('');
      setConfirmPassword('');
    } catch (err) {
      showSnackbar(err.message || 'Erro ao alterar a senha', 'error');
    } finally {
      setLoading(false);
    }
  };

  return {
    currentPassword,
    setCurrentPassword,
    newPassword,
    setNewPassword,
    confirmPassword,
    setConfirmPassword,
    currentPassVisibility,
    newPassVisibility,
    confirmPassVisibility,
    loading,
    handleSubmit,
  };
}
