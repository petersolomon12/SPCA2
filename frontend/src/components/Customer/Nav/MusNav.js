const MusNav = () => {
  const userObject = localStorage.getItem("User");
  var userObj = JSON.parse(userObject);
  const firstname = userObj.firstname;
  const lastname = userObj.lastname;
  return ( 
      
      <aside id="default-sidebar" class="fixed mt-0 my-16 top-0 left-0 z-40 w-44 h-screen transition-transform -translate-x-full sm:translate-x-0" aria-label="Sidebar">
      <div class="h-full fixed px-3 py-4 overflow-y-auto bg-gray-50 dark:bg-gray-800 border-r">
        <div class="flex flex-col w-56 bg-white rounded-r-3xl overflow-hidden">
          <div class="flex items-center justify-center h-20 shadow-md">
          <h1 class="text-3xl uppercase text-indigo-500">{firstname}</h1>
          </div>
          <ul class="flex flex-col py-4">
            <li>
              <a href="/productHome" class="flex flex-row items-center h-12 transform hover:translate-x-2 transition-transform ease-in duration-200 text-gray-500 hover:text-gray-800">
                <span class="inline-flex items-center justify-center h-12 w-12 text-lg text-gray-400"><i class="bx bx-home"></i></span>
                <span class="text-sm font-medium">Home</span>
              </a>
            </li>
            <li>
              <a href="/cart" class="flex flex-row items-center h-12 transform hover:translate-x-2 transition-transform ease-in duration-200 text-gray-500 hover:text-gray-800">
                <span class="inline-flex items-center justify-center h-12 w-12 text-lg text-gray-400"><i class="bx bx-music"></i></span>
                <span class="text-sm font-medium">Shopping Cart</span>
              </a>
            </li>
            <li>
              <a href="/login" class="flex flex-row items-center h-12 transform hover:translate-x-2 transition-transform ease-in duration-200 text-gray-500 hover:text-gray-800">
                <span class="inline-flex items-center justify-center h-12 w-12 text-lg text-gray-400"><i class="bx bx-music"></i></span>
                <span class="text-sm font-medium">Logout</span>
              </a>
            </li>
          </ul>
        </div>
        </div>
      </aside>
   );
}

export default MusNav;