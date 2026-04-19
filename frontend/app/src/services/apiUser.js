
export async function getUsers() {
  const token = localStorage.getItem('token'); 

  const response = await fetch('http://localhost:8081/api/user', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    throw new Error('Erro ao buscar usuários');
  }

  const data = await response.json();
  return data;
}

export async function deleteUser(id) {
  const storedUser = localStorage.getItem('user');
  if (!storedUser) throw new Error('Usuário não autenticado');

  const user = JSON.parse(storedUser);
  const token = user.token;

  const response = await fetch(`http://localhost:8081/api/user/${id}`, {
    method: 'DELETE',
    headers: { Authorization: `Bearer ${token}` },
  });

  if (!response.ok) {
    throw new Error('Erro ao excluir usuário');
  }

  return id; 
}

export async function changePassword(currentPassword, newPassword) {
  const token = localStorage.getItem('token');
  if (!token) throw new Error('Usuário não autenticado');

  const response = await fetch('http://localhost:8081/api/user/change-password', {
    method: 'PUT',
    headers: { 
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify({ currentPassword, newPassword }),
  });

  const text = await response.text();

  if (!response.ok) {
    let data;
    try {
      data = text ? JSON.parse(text) : null;
    } catch {
      throw new Error(`Erro: status ${response.status} e resposta inválida do servidor`);
    }
    throw new Error((data && data.message) || `Erro: status ${response.status}`);
  }

  return text ? JSON.parse(text) : null;

}
