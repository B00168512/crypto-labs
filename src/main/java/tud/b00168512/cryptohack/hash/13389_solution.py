import hashlib
from pwn import remote

FLAG = "crypto{???????????????????????????????????}"


class DocumentStorageChallenge:
    """
    A challenge that allows users to store documents, checking for duplicates
    and limiting the number of stored documents.
    """
    def __init__(self):
        """
        Initializes the DocumentStorageChallenge with a welcome message and
        a pre-existing dictionary of document hashes and their content.
        """
        self.welcome_message = "Give me a document to store\n"
        self.stored_documents = {
            "508dcc4dbe9113b15a1f971639b335bd": b"Particle physics (also known as high energy physics) is a branch of physics that studies the nature of the particles that constitute matter and radiation. Although the word particle can refer to various types of very small objects (e.g. protons, gas particles, or even household dust), particle physics usually investigates the irreducibly smallest detectable particles and the fundamental interactions necessary to explain their behaviour.",
            "cb07ff7a5f043361b698c31046b8b0ab": b"The Large Hadron Collider (LHC) is the world's largest and highest-energy particle collider and the largest machine in the world. It was built by the European Organization for Nuclear Research (CERN) between 1998 and 2008 in collaboration with over 10,000 scientists and hundreds of universities and laboratories, as well as more than 100 countries.",
        }
        self.exit = False

    def process_user_input(self, user_message):
        """
        Processes a message from the user, expecting a 'document' field
        containing the hexadecimal representation of the document to store.

        Args:
            user_message (dict): A dictionary containing the user's input,
                                 expected to have a 'document' key.

        Returns:
            dict: A dictionary containing the result of the operation,
                  either an error message or a success message.
        """
        if "document" not in user_message:
            self.exit = True
            return {"error": "You must send a document"}

        try:
            received_document_bytes = bytes.fromhex(user_message["document"])
        except ValueError:
            self.exit = True
            return {"error": "Invalid hexadecimal format for the document"}

        document_hash = hashlib.md5(received_document_bytes).hexdigest()

        if document_hash in self.stored_documents:
            self.exit = True
            if self.stored_documents[document_hash] == received_document_bytes:
                return {"error": "Document already exists in the system"}
            else:
                return {"error": f"Document system crash, leaking flag: {FLAG}"}

        self.stored_documents[document_hash] = received_document_bytes

        if len(self.stored_documents) > 5:
            self.exit = True
            return {"error": "Too many documents in the system"}

        return {"success": f"Document {document_hash} added to system"}


"""
This block describes how the 'process_user_input' function is intended to be used
when a connection is established. It expects JSON input with a 'document' field.
"""
# listener.start_server(port=13389)

# Example interaction to trigger the flag leak
first_malicious_hash = "4dc968ff0ee35c209572d4777b721587d36fa7b21bdc56b74a3dc0783e7b9518afbfa200a8284bf36e8e4b55b35f427593d849676da0d1555d8360fb5f07fea2"
second_malicious_hash = "4dc968ff0ee35c209572d4777b721587d36fa7b21bdc56b74a3dc0783e7b9518afbfa202a8284bf36e8e4b55b35f427593d849676da0d1d55d8360fb5f07fea2"

remote_connection = remote('socket.cryptohack.org', 13389)

print(remote_connection.recvline().decode())

# Send the first document
payload_1 = f'{{"document":"{first_malicious_hash}"}}'
print(f"Sending: {payload_1}")
remote_connection.sendline(payload_1.encode())
print(remote_connection.recvline().decode())

payload_2 = f'{{"document":"{second_malicious_hash}"}}'
print(f"Sending: {payload_2}")
remote_connection.sendline(payload_2.encode())
print(remote_connection.recvline().decode())

remote_connection.close()