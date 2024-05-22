import {createContext, useEffect, useState} from "react";
import * as categoryService from "../services/CategoryService";

export const CategoryContext = createContext({
    categories: []
});

export default function CategoryProvider({children}) {
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        categoryService.getCategories()
            .then(r => setCategories(r.data))
            .catch(e => console.error(e));
    }, []);

    return (
        <CategoryContext.Provider value={{
            categories: categories
        }}>
            {children}
        </CategoryContext.Provider>
    );
}