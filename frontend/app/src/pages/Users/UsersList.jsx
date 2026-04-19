import DeleteIcon from '@mui/icons-material/Delete';
import IconButton from '@mui/material/IconButton';
import { DataGrid } from '@mui/x-data-grid';
import useUsers from '../../hooks/useUsers';
import Layout from '../../components/layouts/MainLayout';
import { Box, CircularProgress, Typography, Alert } from '@mui/material';
import { useAuth } from '../../contexts/AuthContext'; 

export default function UsersList() {
  const { users, loading, erro, excludeUser } = useUsers();
  const { user, logout } = useAuth(); 

  // função que faz a exclusão e desloga se for você mesmo
  const handleExcludeUser = async (id) => {
    try {
      await excludeUser(id);

      if (id.toString() === user.id.toString()) {
        logout();
        window.location.href = '/login';
      }
    } catch (error) {
      alert(error.message || 'Erro ao excluir usuário');
    }
  };

  const colunas = [
    { field: 'id', headerName: 'ID', width: 90 },
    { field: 'name', headerName: 'Nome', flex: 1 },
    { field: 'email', headerName: 'E-mail', flex: 1 },
    { field: 'cpf', headerName: 'CPF', flex: 1 },
    { field: 'role', headerName: 'Perfil', flex: 1 },
    {
      field: 'acoes',
      headerName: 'Ações',
      width: 100,
      renderCell: (params) => (
        <IconButton
          onClick={() => handleExcludeUser(params.row.id)} 
          color="error"
        >
          <DeleteIcon />
        </IconButton>
      ),
    },
  ];

  return (
    <Layout>
      <Typography variant="h5" gutterBottom>
        Lista de Usuários
      </Typography>

      {loading && <CircularProgress />}
      {erro && <Alert severity="error">{erro}</Alert>}

      {!loading && !erro && (
        <Box sx={{ height: 400, mt: 2 }}>
          <DataGrid
            rows={users}
            columns={colunas}
            pageSize={5}
            rowsPerPageOptions={[5]}
            disableSelectionOnClick
          />
        </Box>
      )}
    </Layout>
  );
}
