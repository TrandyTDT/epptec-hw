const form = document.querySelector('form');
const firstNameInput = document.getElementById('firstName');
const lastNameInput = document.getElementById('lastName');
const nationalIdInput = document.getElementById('nationalId');
const addPersonButton = document.getElementById('addPersonButton');
const peopleList = document.getElementById('peopleList');

const deletePersonButton = document.getElementById('deletePersonButton');
const nationalIdDeleteInput = document.getElementById('nationalIdDelete')

const findPersonButton = document.getElementById('findPersonButton')
const nationalIdFindInput = document.getElementById('nationalIdFind')
const resultDiv = document.getElementById('result');

function addPerson(event) {
    event.preventDefault();

    const firstName = firstNameInput.value;
    const lastName = lastNameInput.value;
    const nationalId = nationalIdInput.value;

    const person = {
        firstName,
        lastName,
        nationalId
    };

    fetch('http://localhost:8080/api/person/', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(person)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Chyba při přidávání osoby.');
            }
        })
        .then(() => {
            firstNameInput.value = '';
            lastNameInput.value = '';
            nationalIdInput.value = '';
            fetchPeople();
        })
        .catch(error => {
            console.error(error);
        });
}

function deletePerson(event) {
    event.preventDefault();

    const nationalId = nationalIdDeleteInput.value;

    fetch('http://localhost:8080/api/person/'+nationalId, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Chyba při odstraňování osoby.');
            }
        })
        .then(() => {
            nationalIdDeleteInput.value = '';
            fetchPeople();
        })
        .catch(error => {
            console.error(error);
        });
}

function findPerson(event) {
    event.preventDefault();

    const nationalId = !nationalIdFindInput.value ? null : nationalIdFindInput.value;

    fetch('http://localhost:8080/api/person/'+nationalId)
        .then(response => {
            if (!response.ok) {
                throw new Error('Chyba při hledání osoby.');
            }
            return response.json();
        })
        .then((personWithAge) => {
            nationalIdFindInput.value = '';
            renderSearchResult(personWithAge);
        })
        .catch(error => {
            console.error(error);
        });
}

function renderSearchResult (personWithAge) {
    const personHTML = '<span>'+personWithAge.person.firstName+' ' + personWithAge.person.lastName + ' - ' + personWithAge.person.nationalId + ' - Věk: ' + personWithAge.age + '</span>';
    resultDiv.innerHTML = personHTML;
}

function fetchPeople() {
    fetch('http://localhost:8080/api/person/')
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Chyba při získávání seznamu osob.');
            }
        })
        .then(people => {
            const peopleHTML = people.map(person => `<li>${person.firstName} ${person.lastName} - ${person.nationalId}</li>`).join('');
            peopleList.innerHTML = peopleHTML;
        })
        .catch(error => {
            console.error(error);
        });
}

addPersonButton.addEventListener('click', addPerson);
deletePersonButton.addEventListener('click', deletePerson);
findPersonButton.addEventListener('click', findPerson);

fetchPeople();
