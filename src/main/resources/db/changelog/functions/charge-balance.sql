CREATE FUNCTION charge_balance(add_percent int, max_percent int)
    RETURNS void
    LANGUAGE plpgsql
AS
$$
BEGIN
UPDATE clients
SET curr_balance = calc_new_balance(add_percent, max_percent, init_balance, curr_balance)
WHERE curr_balance < init_balance * max_percent / 100;
END;
$$;