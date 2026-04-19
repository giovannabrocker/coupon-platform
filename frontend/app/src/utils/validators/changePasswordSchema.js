// src/validators/changePasswordSchema.js
import * as yup from 'yup';

export const changePasswordSchema = yup.object().shape({
  currentPassword: yup.string().required('Senha atual é obrigatória'),
  newPassword: yup.string()
    .required('Nova senha é obrigatória')
    .min(8, 'Senha deve ter pelo menos 8 caracteres'),
  confirmPassword: yup.string()
    .required('Confirme a nova senha')
    .oneOf([yup.ref('newPassword')], 'Confirmação da senha não confere'),
});
