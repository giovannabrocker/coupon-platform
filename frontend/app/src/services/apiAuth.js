export async function login(email, password) {
  try {
    const response = await fetch('http://localhost:8081/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password }),
    });

    const text = await response.text();

    let data;
    try {
      data = JSON.parse(text);
    } catch (e) {
      throw new Error(text);
    }

    if (!response.ok) {
      throw new Error(data.message || 'Erro ao fazer login');
    }

    localStorage.setItem('token', data.token);
    return data;
  } catch (err) {
    console.error('Erro no login:', err);
    throw err;
  }
}

export async function register({ name, email, password, cpf, role }) {
  try {
    const response = await fetch('http://localhost:8081/auth/register', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name, email, password, cpf, role }),
    });

    const text = await response.text();
    let data = null;

    try {
      data = JSON.parse(text);
    } catch (e) {
      if (!response.ok) {
        throw new Error(text || 'Erro desconhecido no servidor');
      }
      throw new Error('Resposta inválida do servidor');
    }

    if (!response.ok) {
      throw new Error(data?.message || 'Erro ao fazer cadastro');
    }

    return data;
  } catch (err) {
    console.error('Erro no cadastro:', err);
    throw err;
  }
}