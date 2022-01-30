let data = {};

export default {
    addSelectedSubject : (selectedSubject) => {
        console.log(`writing ${selectedSubject} to store`);
        data.selectedSubject = selectedSubject;        
    },
    getSelectedSubject : () => {
        return data.selectedSubject;
    }
};
