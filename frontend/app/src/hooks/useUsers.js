import { useState, useEffect } from 'react';
import { getUsers, deleteUser } from '../services/apiUser';

export default function useUsers() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(false);
  const [erro, setErro] = useState(null);

  useEffect(() => {
    async function fetchUsers() {
      setLoading(true);
      setErro(null);
      try {
        const data = await getUsers();
        setUsers(data);
      } catch (error) {
        setErro(error.message || 'Erro desconhecido');
      } finally {
        setLoading(false);
      }
    }
    fetchUsers();
  }, []);

  async function excludeUser(id) {
    await deleteUser(id);
    setUsers((prevUsers) => prevUsers.filter((u) => u.id !== id));
  }

  return { users, loading, erro, excludeUser };
}

