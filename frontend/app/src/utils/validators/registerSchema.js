import * as yup from 'yup';

export const registerSchema = yup.object().shape({
  name: yup.string().required('Nome é obrigatório'),
  email: yup.string().email('E-mail inválido').required('E-mail é obrigatório'),
  password: yup.string().min(8, 'Senha deve ter no mínimo 8 caracteres').required('Senha é obrigatória'),
  cpf: yup.string()
    .matches(/^\d{11}$/, 'CPF deve ter 11 dígitos numéricos')
    .required('CPF é obrigatório'),
  role: yup.string()
    .oneOf(['USER', 'ADMIN'], 'Perfil inválido')
    .required('Perfil é obrigatório'),
});
