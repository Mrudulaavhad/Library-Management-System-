import { Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField } from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";

interface EditBookPopupProps {
    isOpen: boolean;
    onClose: () => void;
    onUpdateClick: (bookData: any) => void;
    bookData: {
        id: string;
        title: string;
        userNames: string[];
    };
}

const EditBookPopup: React.FC<EditBookPopupProps> = ({
    isOpen,
    onClose,
    onUpdateClick,
    bookData,
}) => {
    const [title, setTitle] = useState(bookData.title);
    const [userNames, setUserNames] = useState(bookData.userNames.join(',')); 

    

    const handleUpdate = async () => {
        try {
            const updatedBookData = {
                id: bookData.id,
                title: title,
                userNames: userNames.split(','), 
            };
    
            const response = await axios.put(
                `http://localhost:8080/api/books/${bookData.id}`,
                updatedBookData
            );
    
            if (response.status === 200) {
                console.log("Book updated successfully");
                onUpdateClick(updatedBookData);
                onClose();
            } else {
                console.error("Error updating book");
            }
        } catch (error) {
            console.error("Error updating book:", error);
        }
    };
    

    return (
        <div>
            <Dialog open={isOpen} onClose={onClose} className="dialog-container">
                <DialogTitle>Edit Book</DialogTitle>
                <hr />
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        className="title"
                        id="title"
                        label="Title"
                        type="text"
                        variant="outlined"
                        fullWidth
                        required
                        value={title}
                        onChange={(event) => setTitle(event.target.value)}
                    />
                    <TextField
                        autoFocus
                        margin="dense"
                        className="userNames"
                        id="userNames"
                        label="User Names (comma-separated)"
                        type="text"
                        variant="outlined"
                        fullWidth
                        required
                        value={userNames}
                        onChange={(event) => setUserNames(event.target.value)}
                    />
                </DialogContent>
                <DialogActions>
                    <Button className="popup-cancel-button" onClick={onClose}>
                        Cancel
                    </Button>
                    <Button className="popup-add-button" onClick={handleUpdate}>
                        Update Book
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
};

export default EditBookPopup;
