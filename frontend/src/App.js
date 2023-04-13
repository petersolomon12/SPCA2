import Register from './components/Register/Register';
import Signin from './components/Signin/Signin';
import AdminNav from './components/Admin/Nav/AdminNav';
import CreateProduct from './components/Admin/CreateProduct';
import Products from './components/Admin/Products';
import Customers from './components/Admin/Customer';
import ProductHome from './components/Customer/Home/ProductHome';
import ShoppingCart from './components/Customer/Cart/ShoppingCart';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import './App.css';

function App() {
  return (
    <Router>
    <Switch>
    <Route exact path="/register" component={Register} />
    <Route exact path="/login" component={Signin} />
    <Route exact path="/createProduct" component={CreateProduct} />
    <Route exact path="/products" component={Products} />
    <Route exact path="/customers" component={Customers} />
    <Route exact path="/productHome" component={ProductHome} />
    <Route exact path="/cart" component={ShoppingCart} />
      </Switch>
      </Router>
  );
}

export default App;
