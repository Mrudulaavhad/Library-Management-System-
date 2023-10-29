//AddBookPopUp.tsx file
import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { useState } from 'react';



export interface BookData {
  id: string;
  title: string;
  userNames: string[];
  userId: string;

}


interface AddBookDialogProps {
  open: boolean;
  handleClose: () => void; 
  handleAddBook: (newBook: BookData) => void;


}


export default function AddBookPopUp({ open, handleClose, handleAddBook
}: AddBookDialogProps) {




  const [bookInfo, setBookInfo] = useState<BookData>({
    id: '',
    title: '',
    userNames: [],
    userId: '', 
  });







  const handleInputChange = async (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setBookInfo({
      ...bookInfo,
      [name]: value,
    });
    if (name === "userId") {
      const fetchedUserName = await fetchUserName(value);
      if (fetchedUserName) {
        setBookInfo({
          ...bookInfo,
          userNames: [fetchedUserName],
        });
      } else {
      
      }
    }
    console.log('Input field name:', name);
console.log('Input field value:', value);

  };

  const fetchUserName = async (userId: string) => {
    try {
      const response = await fetch(`http://localhost:8080/api/users/${userId}`);
      if (response.ok) {
        const userData = await response.json();
        return userData.name; // Return the fetched username
      } else {
        console.error('Failed to fetch user name');
        return "";
      }
      console.log('Fetching user name for user ID:', userId);
    } catch (error) {
      console.error('Error fetching user name:', error);
      return "";
    }
  };


 

  //to add book
  const handleAddBookClick = () => {
    if (!bookInfo.title.trim() ) {

      alert('Title is required fields.');
      return;
    }
    let userNamesString: string;

    const newBookData: BookData = {
      title: bookInfo.title,
    userNames: bookInfo.userNames, 
    userId: bookInfo.userId,
    id: '',
    };
    handleAddBook(newBookData);
    

    handleClose();

    //  creating the book
  fetch('http://localhost:8080/api/books', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(newBookData),
  })
    .then((response) => response.json()) // Parse the response as JSON
    .then((createdBook) => {
    fetchUserName(newBookData.userId)
    .then((userName) => {
     
      newBookData.userNames = [userName || '']; 
      handleAddBook(newBookData);

      handleClose();
    })
    .catch((error) => {
      console.error('Error creating book:', error);
    });
});
  };

  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogTitle>
        <h3>Add Book</h3>
      </DialogTitle>

      <DialogContent>
        <DialogContentText>
          Please enter the details of the book you want to add.
        </DialogContentText>

        <TextField
          autoFocus
          margin="dense"
          id="title"
          name="title"
          label="Book Title"
          type="text"
          fullWidth
          size='small'
          variant="outlined"
          value={bookInfo.title}
          onChange={handleInputChange}
          InputLabelProps={{ shrink: true }}
        />

        <TextField
          margin="dense"
          id="userNames"
          name="userNames"
          label="User Name"
          type="text"
          fullWidth
          size='small'
          variant="outlined"
          value={bookInfo.userNames}
          onChange={handleInputChange}
          InputLabelProps={{ shrink: true }}
        />
        <TextField
          margin="dense"
          id="userId"
          name="userId"
          label="User ID"
          type="text"
          fullWidth
          size='small'
          variant="outlined"
          value={bookInfo.userId}
          onChange={handleInputChange}
          InputLabelProps={{ shrink: true }}
        />


      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose}>Cancel</Button>


        <Button onClick={handleAddBookClick} color="primary" style={{ backgroundColor: 'blue', color: 'white' }} >
          Add Book
        </Button>

      </DialogActions>

    </Dialog>
  );
};


