import { useState } from 'react';
import { register } from '../services/apiAuth';
import { registerSchema } from '../utils/validators/registerSchema';

export default function useRegister() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleRegister = async ({ name, email, password, cpf, role }) => {
    setError(null);
    try {
      await registerSchema.validate({ name, email, password, cpf, role });

      setLoading(true);
      const user = await register({ name, email, password, cpf, role });
      return user;
    } catch (err) {
      setError(err.message);
      throw err;
    } finally {
      setLoading(false);
    }
  };

  return {
    register: handleRegister,
    loading,
    error,
  };
}
