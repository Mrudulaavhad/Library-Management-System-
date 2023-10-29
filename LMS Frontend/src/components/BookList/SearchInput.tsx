import React from 'react';
import SearchIcon from '@mui/icons-material/Search';
import './SearchInput.scss';


interface SearchInputProps {
  searchQuery: string;
  handleSearchChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  placeholder: string;
}

const SearchInput: React.FC<SearchInputProps> = ({
  searchQuery,
  handleSearchChange,
  placeholder,
}) => {
  return (
    <div className="search-input-container">
      <input
        className="search-input"
        type="text"
        placeholder={placeholder}
        value={searchQuery}
        onChange={handleSearchChange}
      />
      <div className="search-icon">
        <SearchIcon />
      </div>
    </div>
  );
};

export default SearchInput;
