//Usertable.tsx
import React, { useEffect, useState } from 'react';
import { DataGrid, GridColDef, GridRowClassNameParams, GridRowParams } from '@mui/x-data-grid';
import './UserTable.scss';
import { useNavigate } from 'react-router-dom';
import { IconButton } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import SearchInput from '../BookList/SearchInput';
import instance from '../utils/axiosInstance';

interface UserTableData {
  id: number;
  name: string;
  bookIssued: string;
  bookIssuedDate: string;
  phoneNumber: string;
  books: string[];
}

const UserTable: React.FC = () => {

  const [Data, setData] = useState<UserTableData[]>([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [filteredData, setFilteredData] = useState<UserTableData[]>(Data);





  const initialValues = {
    name: "",
    phoneNumber: "",
    issueOnDate: "",
    issuedTillDate: "",
  };


  const navigate = useNavigate();


  const handleSearchInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const query = event.target.value;
    setSearchQuery(query);

    if (query.trim() === '') {
      setFilteredData(Data);
    } else {
      const filteredData = Data.filter((item) =>
        item.name.toLowerCase().includes(query.toLowerCase())
      );
      setFilteredData(filteredData);
    }
  };

  const handleAddUser = (): void => {
    navigate('/create-user');
  };


  const handleEditUser = (user: UserTableData) => {
    navigate('/create-user', { state: { editingUser: user } });
  };




  const handleDeleteUser = (id: number) => {
    instance
      .delete(`/api/users/${id}`)
      .then((response) => {
        if (response.status === 204) {
          console.log(`User with ID ${id} deleted successfully`);
          // Update your data state to remove the deleted user
          setData((prevData) => prevData.filter((user) => user.id !== id));
          setFilteredData((prevData) => prevData.filter((user) => user.id !== id));
        } else {
          console.error('Failed to delete user');
        }
      })
      .catch((error) => {
        console.error('Error deleting user:', error);
      });
  };



  //to get all users 
  // useEffect(() => {
  //   fetch('http://localhost:8080/api/users/with-issued-books')
  //     .then((response) => {
  //       if (!response.ok) {
  //         throw new Error('Failed to fetch data');
  //       }
  //       return response.json();
  //     })
  //     .then((data) => {
  //       setData(data);
  //       setFilteredData(data);
  //     })
  //     .catch((error) => {
  //       console.error('Error fetching data:', error);

  //     });
  // }, []);


  useEffect(() => {
    instance
      .get('/api/users/with-issued-books')
      .then((response) => {
        if (response.status == 200) {
          const data = response.data; // Access data directly
          setData(data);
          setFilteredData(data);
        } else {
          throw new Error('Failed to fetch data');
        }
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
      });
  }, []);
  
  

  const columns: GridColDef[] = [
    { field: 'id', headerName: 'ID', width: 120 },
    { field: 'name', headerName: 'Name', width: 250 },
    {
      field: 'books',
      headerName: 'Books',
      width: 930,
      renderCell: (params) => (
        <span>{params.value.join(', ')}</span>
      ),
    },
   
    {
      field: 'actions',
      headerName: '',
      width: 190,
      renderCell: (params) => (
        <div>
          <IconButton
            onClick={() => handleEditUser(params.row)}

          >
            <EditIcon />
          </IconButton>
          <IconButton
            onClick={() => handleDeleteUser(params.row.id)}

          >
            <DeleteIcon />
          </IconButton>
        </div>
      ),
    },
  ];

  return (
    <div className="usertable-container">
      <div className="usertable-title">
       <div className='h3'> <h3>UserList</h3></div>
      </div>


      <div className="add-User-button">
        <button className="button" onClick={handleAddUser}>Add User</button>

        <SearchInput
          searchQuery={searchQuery}
          handleSearchChange={handleSearchInputChange}
          placeholder="Search User "
        />
      </div>





      <div className="table-wrapper">
        <DataGrid
          key={Date.now()}
          rows={filteredData}
          columns={columns}
          initialState={{
            pagination: {
              paginationModel: { page: 0, pageSize: 10 },
            },
          }}
          pageSizeOptions={[5, 10, 20]}
          checkboxSelection
          className="custom-data-grid"
          getRowClassName={(params) =>
            params.indexRelativeToCurrentPage % 2 === 0 ? 'Mui-even' : 'Mui-odd'
          }


        />


        {/* <DataGrid
          key={Date.now()}
          rows={filteredData}
          columns={columns}

          className="custom-data-grid"
          getRowClassName={(params) =>
            params.indexRelativeToCurrentPage % 2 === 0 ? 'Mui-even' : 'Mui-odd'} />
        <div className="pagination-container">
          
          <Pagination
            count={Math.ceil(filteredData.length / pageSize)}
            page={currentPage}
            onChange={(event, newPage) => handleChangePage(newPage)}
          />
        </div> */}

      </div>



    </div>
  );
};

export default UserTable;


