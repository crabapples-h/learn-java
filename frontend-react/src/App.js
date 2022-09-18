import React from 'react';
import Form from './views/Form';
import Context from "./utils/ContextUtils";

function App() {
    return (
        <div>
            <Context.Provider value={{aa:'aa',bb:124}}>
                <Form demo={123}>哈哈</Form>
            </Context.Provider>
        </div>
    );
}

// class App extends React.Component {
//     render() {
//         return (
//             <div>
//                 <Context.Provider value="哈哈哈">
//                     <Form/>
//                 </Context.Provider>
//             </div>
//         );
//     }
// }

export default App;
