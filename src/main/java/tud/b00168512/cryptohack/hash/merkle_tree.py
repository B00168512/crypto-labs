from hashlib import sha256

def hash256(data):
    return sha256(data).digest()

def merge_nodes(a, b):
    return hash256(a + b)

def verify_merkle_proof(a, b, c, d, root):
    left = merge_nodes(bytes.fromhex(a), bytes.fromhex(b))
    right = merge_nodes(bytes.fromhex(c), bytes.fromhex(d))
    calculated_root = merge_nodes(left, right)
    return calculated_root.hex() == root

with open("output.txt", "r") as f:
    bits = ""
    for nextLine in f:
        a, b, c, d, root = eval(nextLine.strip())
        if verify_merkle_proof(a, b, c, d, root):
            bits += "1"
        else:
            bits += "0"
print(bits)
"""110001101110010011110010111000001110100011011110111101101010101010111110110000101110010011001010101111101010010001100110110000101100100011110010101111101000110011011110111001001011111010100110011010001110000011011000110100101101110011100110101111101100011011010000011010001101100011011000111001101111101"""
# """crypto{U_are_R3ady_For_S4plins_ch4lls}"""