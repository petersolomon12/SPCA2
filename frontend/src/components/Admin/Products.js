import axios from "../../api/axios";
import React, { useEffect, useMemo, useState } from 'react';
import { useTable, useFilters } from 'react-table';
import AdminNav from "./Nav/AdminNav";
 

const CategoryFilter = ({ column: { filterValue, setFilter } }) => {
    return (
      <input
        value={filterValue || ''}
        onChange={(e) => {
          setFilter(e.target.value || undefined);
        }}
      />
    );
  };

  const DefaultFilter = ({ column: { filterValue, setFilter } }) => {
    return (
      <input
        value={filterValue || ''}
        onChange={(e) => {
          setFilter(e.target.value || undefined);
        }}
      />
    );
  };

const Products = () => {

    const adminProducts = '/viewAdminItems';
    const updateStock = '/updateStock';
    const userObject = localStorage.getItem("User");
    var userObj = JSON.parse(userObject);
    const id = userObj.id;
    const [data, setData] = useState([]);
    const [showModal1, setShowModal1] = useState(false);
    const [stockLevel, setStockLevel] = useState('');
    const [productId, setProductId] = useState('')



    useEffect(() => {
       
        const data = JSON.stringify({ id });
        // if button enabled with JS hack

        try {

            axios.post(adminProducts, data,
                {
                    headers: { 'Content-Type': 'application/json' },
                    "Accept": 'application/json',
                    withCredentials: true
                }).then((res) => {
                    setData(res.data);
                })

        } catch (err) {
            console.log(err)

        }
      }, []);

      const toggleModal1 = (id) => {
        setShowModal1(!showModal1);
        setProductId(id);
    };


  const columns = useMemo(
    () => [
      {
        Header: 'Name',
        accessor: 'name',
      },
      {
        Header: 'Category',
        accessor: 'category',
        Filter: CategoryFilter,
      },
      {
        Header: 'Stock Level',
        accessor: 'stockLevel',
      },
      {
        Header: 'Add To Cart',
        accessor: 'id',
        Cell: ({ row }) => (
          <button onClick={() => toggleModal1(row.original.id)}>
            Add to cart
          </button>
        ),
      },
    ],
    []
  );

  const defaultColumn = useMemo(
    () => ({
      Filter: DefaultFilter,
    }),
    []
  );

  const {
    getTableProps,
    getTableBodyProps,
    headerGroups,
    rows,
    prepareRow,
    setFilter,
  } = useTable(
    {
      columns,
      data: data || [], // add null check here
      defaultColumn,
    },
    useFilters
  );

  const handleSubmit = async () => {

    const id = productId;

    
  
      try{
          const response = await axios.post(updateStock,
            JSON.stringify({id, stockLevel}),
              {
                  headers: { 'Content-Type': 'application/json' },
                  "Accept" : 'application/json',
                  withCredentials: true
              }).then((result) => {
              alert('Succesfully Updated Stock Level')
                    window.location.reload()
              })
        } catch (err) {
          }
  }


      
    return ( 
        <>
        <AdminNav />

        <div className="ml-96">
        <input
          type="text"
          placeholder="Search by category"
          onChange={(e) => {
            setFilter('category', e.target.value);
          }}
        />
        <table class="w-full border-collapse bg-white text-left text-sm text-gray-500" {...getTableProps()}>
          <thead class="bg-gray-50">
            {headerGroups.map((headerGroup) => (
              <tr {...headerGroup.getHeaderGroupProps()}>
                {headerGroup.headers.map((column) => (
                  <th class="px-6 py-4 font-medium text-gray-900" {...column.getHeaderProps()}>{column.render('Header')}</th>
                ))}
              </tr>
            ))}
          </thead>
          <tbody class="divide-y divide-gray-100 border-t border-gray-100" {...getTableBodyProps()}>
            {rows.map((row) => {
              prepareRow(row);
              return (
                <tr {...row.getRowProps()}>
                  {row.cells.map((cell) => (
                    <td {...cell.getCellProps()}>{cell.render('Cell')}</td>
                  ))}
                </tr>
              );
            })}
          </tbody>
          {showModal1 ? (
                            <div id="small-modal" tabindex="-1" style={{ paddingLeft: "19.5rem" }} class="fixed top-0 left-0 right-0 z-50 w-full p-4 overflow-x-hidden overflow-y-auto md:inset-0 h-modal md:h-full">
                                <div class="relative w-full h-full max-w-md md:h-auto">
                                    <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
                                        <div class="flex items-center justify-between p-5 border-b rounded-t dark:border-gray-600">
                                            <h3 class="text-xl font-medium text-gray-900 dark:text-white">
                                                Edit Profile
                                            </h3>


                                            <button onClick={toggleModal1} style={{ paddingLeft: "13.5rem" }} type="button" class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-hide="small-modal">
                                                <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>
                                                <span class="sr-only">Close modal</span>
                                            </button>
                                        </div>

                                        <div className="pl-10">
                                            <div className="pb-10">
                                                <div className="flex" style={{ alignItems: "flex-start", flexDirection: "column" }}>
                                                    <label style={{ margin: "0 0 0.2rem", fontSize: "0.8rem", color: "rgba(0,0,0,0.6)" }} className="font-normal">
                                                        Stock Level*
                                                    </label>
                                                    <input style={{ boxShadow: "inset 0 0 0 1px" }} id="" value={stockLevel} onChange={(e) => setStockLevel(e.target.value)} placeholder="" required type="text" />
                                                </div>
                                            </div>

                                            <button onClick={handleSubmit} data-modal-hide="small-modal" type="button" class="mb-4 text-white bg-gray-400 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Save</button>

                                        </div>

                                        <div className="pl-10 mb-4">

                                        </div>
                                    </div>
                                </div>
                            </div>

                        ) : null}
        </table>
        </div>
      </>
     );

  

}


 
export default React.memo(Products);