// import React from 'react';
// import MuiPagination from '@mui/material/Pagination';
// import { TablePaginationProps } from '@mui/material/TablePagination';
// import { DataGrid, gridPageCountSelector, GridPagination, useGridApiContext, useGridSelector } from '@mui/x-data-grid';

// function Pagination({
//   page,
//   onPageChange,
//   className,
// }: Pick<TablePaginationProps, 'page' | 'onPageChange' | 'className'>) {
//   const apiRef = useGridApiContext();
//   const pageCount = useGridSelector(apiRef, gridPageCountSelector);

//   return (
//     <MuiPagination
//       color="primary"
//       className={className}
//       count={pageCount}
//       page={page + 1}
//       onChange={(event, newPage) => {
//         onPageChange(event as any, newPage - 1);
//       }}
//     />
//   );
// }

// function CustomPagination(props: any) {
//   return <GridPagination ActionsComponent={Pagination} {...props} />;
// }




// const Pagination = ({
//     totalPosts,
//     postsPerPage,
//     setCurrentPage,
//     currentPage,
// }) => {
//     let pages = [];

//     for (let i = 1; i <= Math.ceil(totalPosts / postsPerPage); i++) {
//         pages.push(i);
//     }

//     return (
//         <div className='pagination'>
//             {pages.map((page, index) => {
//                 return (
//                     <button
//                         key={index}
//                         onClick={() => setCurrentPage(page)}
//                         className={page == currentPage ? "active" : ""}>
//                         {page}
//                     </button>
//                 );
//             })}
//         </div>
//     );
// };

// export default Pagination;
 


import React from 'react'

function Pagination() {
  return (
    <div>
      
    </div>
  )
}

export default Pagination
