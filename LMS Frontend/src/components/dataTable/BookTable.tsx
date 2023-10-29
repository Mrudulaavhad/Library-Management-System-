//BookTable.tsx file
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import './BookTable.scss';
import '../PopUp/AddBookPopUp';
import { useEffect, useState } from 'react';
import PopUp from '../PopUp/AddBookPopUp';
import { IconButton, Input, InputAdornment, Pagination, Paper, Stack, TextField } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import SearchIcon from '@mui/icons-material/Search';
import AddBookPopUp, { BookData } from '../PopUp/AddBookPopUp';
import axios from 'axios';
import React from 'react';
import EditBookPopup from './EditBookDialog';
import SearchInput from '../BookList/SearchInput';
import instance from '../utils/axiosInstance';






interface BookTableData {
  id: any;
  title: string;
  userNames: string[];

}




const BookTable: React.FC = () => {


  const [isPopupOpen, setIsPopupOpen] = useState(false);
  const [data, setData] = useState<BookTableData[]>([]);
  const [books, setBooks] = useState<any[]>([]);

  const [selectedBook, setSelectedBook] = useState<BookData | null>(null);
  const [isEditBookPopupOpen, setIsEditBookPopupOpen] = useState(false);
  const [searchQuery, setSearchQuery] = useState("");
  const [filteredData, setFilteredData] = useState<BookTableData[]>(data);
  const [userName, setUserName] = useState("");






  //for search
  const handleSearchInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const query = event.target.value;
    setSearchQuery(query);

    if (query.trim() === "") {
      setFilteredData(data);
    } else {
      const filteredData = data.filter((item) =>
        item.title.toLowerCase().includes(query.toLowerCase())
      );
      setFilteredData(filteredData);
    }
  };



  const togglePopup = () => {
    setIsPopupOpen(!isPopupOpen);
  };

  const columns: GridColDef[] = [
    { field: 'id', headerName: 'ID', width: 120 },
    { field: 'title', headerName: 'Title', width: 250 },
    {
      field: 'userNames',
      headerName: 'User Names',
      width: 930,
      renderCell: (params) => (
        <div>
          {params.value.map((userNames: string) => (
            <div key={userName}>{userNames}</div>
          ))}
        </div>
      ),
    },
    {
      field: 'actions',
      headerName: '',
      width: 190,
      
     
      renderCell: (params) => (
        <div>
          <IconButton onClick={() => handleEdit(params.row.id)}>
            <EditIcon />
          </IconButton>


          <IconButton onClick={() => handleDeleteClick(params.row.id)}>
            <DeleteIcon />
          </IconButton>
        </div>
      ),
    },
  ];


  //to edit book 
  const handleEdit = (id: number) => {
    const selected = data.find((book) => book.id === id);
    if (selected) {

      const selectedBookData: BookData = {
        id: selected.id,
        title: selected.title,
        userNames: selected.userNames,
        userId: ''
      };

      setSelectedBook(selectedBookData);
      setIsEditBookPopupOpen(true);
    }
  };




  //to add book

  const handleAddBook = async (newBook: BookData): Promise<void> => {
      instance
      .post('/api/books')
      .then((Response)=>{
      if (Response.status === 201) {

        fetchData();
        togglePopup();
      } else {
        console.error('Failed to add the book');
      }
    })
    .catch ((error) =>{
      console.error('Error adding the book:', error);
    })
  };





  const handleDeleteClick = (id: any) => {
    instance
    .delete(`/api/books/${id}`)
    .then((response) => {
      if (response.status === 200) {
        console.log('Book deleted successfully');
        setData((prevData) => prevData.filter((book) => book.id !== id));
        setFilteredData((prevData) => {
          const updatedFilteredData = prevData.filter((book) => book.id !== id);
          console.log('Updated Filtered Data:', updatedFilteredData);
          return updatedFilteredData;
        });
      } else {
        console.error('Failed to delete book');
      }
    })
    .catch((error) => {
      console.error('Error deleting book:', error);
      });
  };




  //to get books

  useEffect(() => {
    instance
      .get('/api/books/books-with-users')
      .then((res) => {
        setData(res.data);
        setFilteredData(res.data);
      })
      .catch((err: any) => console.log(err));
  }, []);


  //after update 
  const fetchData = async () => {
    try {
      const [booksResponse] = await Promise.all([
        instance
          .get('/api/books/books-with-users')

      ]);
      setBooks(booksResponse.data);
      setFilteredData(booksResponse.data);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };






  return (

    <div>
      <AddBookPopUp open={isPopupOpen} handleClose={togglePopup} handleAddBook={handleAddBook} />


      <div className="booktable-container">
        <div className="booklist-title">
        <div className='h3'>  <h3>BookList</h3> </div>
        </div>
        <div className="add-book-button">
          <button className="add-button" onClick={togglePopup}>
            Add Book
          </button>

          <SearchInput
            searchQuery={searchQuery}
            handleSearchChange={handleSearchInputChange}
            placeholder="Search book"
          />
        </div>



        {isPopupOpen && (
          <PopUp
            open={isPopupOpen}
            handleClose={() => {
              togglePopup();

            }}
            handleAddBook={handleAddBook} />
        )}











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
          {/* <div className="table-wrapper">
          // {pageState.isLoading ? (
          //   <div className="loadingScreen">
          //     <CircularProgress />
          //   </div>
          // ) : (
          //   <DataGrid
          //     className="datagrid"
          //     rows={filteredData}
          //     pagination
          //     hideFooterSelectedRowCount
        
          //     page={pageState.page - 1}
          //     pageSize={pageState.pageSize}
          //     onPageChange={(newPage: number) => handlePageChange(newPage + 1)}
          //     onPageSizeChange={handlePageSizeChange}
             
             
          // )} */}




          {isEditBookPopupOpen && selectedBook && (
            <EditBookPopup
              isOpen={isEditBookPopupOpen}
              onClose={() => {
                setIsEditBookPopupOpen(false);
              }}
              onUpdateClick={() => {
                setIsEditBookPopupOpen(false);
                setSelectedBook(null);
                fetchData();
              }}
              bookData={selectedBook}

            />


          )}
        </div>
      </div>
    </div>
  );
};

export default BookTable;



