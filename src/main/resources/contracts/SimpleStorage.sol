// SPDX-License-Identifier: MIT
pragma solidity ^0.6.0;

contract SimpleStorage {

    struct Record {
        address contractAddress;
        uint256 data;
        address creator;
    }

    mapping(uint256 => Record) private records;
    uint256 private nextId;

    event ValueUpdated(uint256 indexed id, uint256 newValue, address indexed creator, address indexed contractAddress);
    event ValueDeleted(uint256 indexed id, address indexed deleter);

    function create(uint256 newData, address contractAddress) public {
        records[nextId].data = newData;
        records[nextId].creator = msg.sender;
        records[nextId].contractAddress = contractAddress;
        emit ValueUpdated(nextId, newData, msg.sender, contractAddress);
        nextId++;
    }

    function update(uint256 id, uint256 newData, address contractAddress) public {
        require(id < nextId, "Record does not exist");
        require(records[id].creator == msg.sender, "Only the creator can update the record");
        records[id].contractAddress = contractAddress;
        records[id].data = newData;
        emit ValueUpdated(id, newData, msg.sender, contractAddress);
    }

    function remove(uint256 id) public {
        require(id < nextId, "Record does not exist");
        require(records[id].creator == msg.sender, "Only the creator can delete the record");

        delete records[id];
        emit ValueDeleted(id, msg.sender);
    }

    function get(uint256 id) public view returns (uint256, address) {
        require(id < nextId, "Record does not exist");
        return (records[id].data, records[id].creator);
    }
}